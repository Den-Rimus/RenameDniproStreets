package ua.dp.rename.dniprostreets.repo;

import androidx.annotation.Nullable;

import com.innahema.collections.query.queriables.Queryable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import timber.log.Timber;
import ua.dp.rename.dniprostreets.api.RenamedObjectsService;
import ua.dp.rename.dniprostreets.entity.ApiDataHolder;
import ua.dp.rename.dniprostreets.entity.CityRegion;
import ua.dp.rename.dniprostreets.entity.LastUpdateHolder;
import ua.dp.rename.dniprostreets.entity.RenamedObject;
import ua.dp.rename.dniprostreets.rx.IoToMainComposer;

public class RenamedObjectsRepo {

    private SnappyRepository db;
    private RenamedObjectsService apiService;

    private List<Listener> listeners = new ArrayList<>();
    private Map<String, CityRegion> asMap = new HashMap<>();
    private List<CityRegion> asList = new ArrayList<>();

    public RenamedObjectsRepo(SnappyRepository db, RenamedObjectsService apiService) {
        this.db = db;
        this.apiService = apiService;
        asList = this.db.getCityRegions();
        asMap = ApiDataHolder.getRegionsAsMap(asList);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Essential public methods
    ///////////////////////////////////////////////////////////////////////////

    public void requestUpdate() {
        apiService.getLastDataUpdateTimestamp()
                .map(LastUpdateHolder::getLastUpdate)
                .filter(this::updateNeeded)
                .compose(new IoToMainComposer<>())
                .subscribe(l -> performUpdate(), e -> {
                    Timber.e(e, "API error");
                    pokeAttachedListenersWithError();
                });
    }

    public List<CityRegion> getRegionsAsList() {
        return asList;
    }

    public Map<String, CityRegion> getRegionsAsMap() {
        return asMap;
    }

    @Nullable
    public CityRegion getRegion(String id) {
        return asMap.get(id);
    }

    public List<RenamedObject> getAllRenamedObjects() {
        final List<RenamedObject> superSet = new ArrayList<>();
        Queryable.from(asList).forEachR(region -> superSet.addAll(region.getObjects()));

        return Queryable.from(superSet).sort(RenamedObject.ALPHABETICAL_COMPARATOR).distinct().toList();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Essential private methods
    ///////////////////////////////////////////////////////////////////////////

    private void performUpdate() {
        apiService.getJson()
                .compose(new IoToMainComposer<>())
                .subscribe(this::dataSetObtained, e -> {
                    pokeAttachedListenersWithError();
                    Timber.e(e, "API error");
                });
    }

    private boolean updateNeeded(long lastUpdate) {
        return db.getLastUpdateTimestamp() < lastUpdate;
    }

    private void dataSetObtained(ApiDataHolder cityData) {
        asList = cityData.getRegionsAsList();
        asMap = ApiDataHolder.getRegionsAsMap(asList);
        pokeAttachedListenersWithSuccess();
        db.putCityRegions(asList);
        db.setLastUpdateTimestamp(System.currentTimeMillis());
    }

    ///////////////////////////////////////////////////////////////////////////
    // Listener
    ///////////////////////////////////////////////////////////////////////////

    public void attachListener(Listener listener) {
        listeners.add(listener);
    }

    public void detachListener(Listener listener) {
        listeners.remove(listener);
    }

    public interface Listener {

        void onDataUpdated();

        void onDataRequestError();
    }

    private void pokeAttachedListenersWithSuccess() {
        checkListeners();
        Queryable.from(listeners).forEachR(Listener::onDataUpdated);
    }

    private void pokeAttachedListenersWithError() {
        checkListeners();
        Queryable.from(listeners).forEachR(Listener::onDataRequestError);
    }

    private void checkListeners() {
        if (listeners.isEmpty()) Timber.w("No attached listeners! Check your setup maybe?");
    }
}
