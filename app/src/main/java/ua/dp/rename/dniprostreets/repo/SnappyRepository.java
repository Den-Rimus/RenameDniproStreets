package ua.dp.rename.dniprostreets.repo;

import android.content.Context;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import timber.log.Timber;
import ua.dp.rename.dniprostreets.core.snappy_core.Optional;
import ua.dp.rename.dniprostreets.di.AppContext;
import ua.dp.rename.dniprostreets.entity.CityRegion;

import javax.inject.Inject;

public class SnappyRepository {

    private static final String LAST_UPDATE_TIMESTAMP = "LAST_UPDATE_TIMESTAMP";
    private static final String CITY_REGION = "CITY_REGION";
    private static final String CACHE_VERSION_KEY = "CACHE_VERSION_KEY";

    static final int CACHE_VERSION = 3;

    private Context context;
    private ExecutorService executorService;

    @Inject
    public SnappyRepository(@AppContext Context context) {
        if (context == null) throw new IllegalArgumentException("Context is null");
        //
        this.context = context;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Action interfaces
    ///////////////////////////////////////////////////////////////////////////

    private interface SnappyAction {
        void call(DB db) throws SnappydbException;
    }

    private interface SnappyResult<T> {
        T call(DB db) throws SnappydbException;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Proxy helpers
    ///////////////////////////////////////////////////////////////////////////

    private void act(SnappyAction action) {
        executorService.execute(() -> {
            DB snappyDb = null;
            try {
                snappyDb = DBFactory.open(context);
                action.call(snappyDb);
            } catch (SnappydbException e) {
                if (isNotFound(e)) Timber.v("Nothing found");
                else Timber.w(e, "DB fails to act", e);
            } finally {
                try {
                    if (snappyDb != null && snappyDb.isOpen()) snappyDb.close();
                } catch (SnappydbException e) {
                    Timber.w(e, "DB fails to close");
                }
            }
        });
    }

    private <T> Optional<T> actWithResult(SnappyResult<T> action) {
        Future<T> future = executorService.submit(() -> {
            DB snappyDb = null;
            try {
                snappyDb = DBFactory.open(context);
                T result = action.call(snappyDb);
                Timber.v("DB action result: %s", result);
                return result;
            } catch (SnappydbException e) {
                if (isNotFound(e)) Timber.v("Nothing found");
                else Timber.w(e, "DB fails to act with result", e);
                return null;
            } finally {
                if (snappyDb != null)
                    try {
                        snappyDb.close();
                    } catch (SnappydbException e) {
                        Timber.w(e, "DB fails to close");
                    }
            }
        });
        try {
            return Optional.fromNullable(future.get());
        } catch (InterruptedException | ExecutionException e) {
            Timber.w(e, "DB fails to act with result");
            return Optional.absent();
        }
    }

    private boolean isNotFound(SnappydbException e) {
        return e.getMessage().contains("NotFound");
    }

    ///////////////////////////////////////////////////////////////////////////
    // Public
    ///////////////////////////////////////////////////////////////////////////

    public <T> void putList(String key, Collection<T> list) {
        act(db -> db.put(key, list.toArray()));
    }

    public <T> List<T> readList(String key, Class<T> clazz) {
        return actWithResult(db -> new ArrayList<>(Arrays.asList(db.getObjectArray(key, clazz))))
                .or(new ArrayList<>());
    }

    /**
     * Method is intended to delete all records for given key.
     *
     * @param key key to be deleted.
     */
    public void clearAllForKey(String key) {
        act(db -> {
            String[] placesKeys = db.findKeys(key);
            for (String placeKey : placesKeys) {
                db.del(placeKey);
            }
        });
    }

    public void clearAll() {
        act(DB::destroy);
    }

    public Boolean isEmptyForKey(String key) {
        return actWithResult((db) -> {
            String[] keys = db.findKeys(key);
            return keys == null || keys.length == 0;
        }).or(false);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Essential methods
    ///////////////////////////////////////////////////////////////////////////

    public void putCityRegions(List<CityRegion> list) {
        putList(CITY_REGION, list);
    }

    public List<CityRegion> getCityRegions() {
        return readList(CITY_REGION, CityRegion.class);
    }

    public void setLastUpdateTimestamp(long lastUpdateTimestamp) {
        act(db -> db.putLong(LAST_UPDATE_TIMESTAMP, lastUpdateTimestamp));
    }

    public long getLastUpdateTimestamp() {
        return actWithResult(db -> db.getLong(LAST_UPDATE_TIMESTAMP)).or(1001L);
    }

    void setCacheVersion(int newVersion) {
        act(db -> db.putInt(CACHE_VERSION_KEY, newVersion));
    }

    int getCacheVersion() {
         return actWithResult(db -> db.getInt(CACHE_VERSION_KEY)).or(0);
    }
}
