package ua.dp.rename.dniprostreets.repo;

import android.support.annotation.Nullable;
import android.util.Log;

import com.innahema.collections.query.queriables.Queryable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ua.dp.rename.dniprostreets.entity.ApiDataHolder;
import ua.dp.rename.dniprostreets.entity.CityRegion;
import ua.dp.rename.dniprostreets.entity.RenamedObject;

public class RenamedObjectsRepo {

    private static final String TAG = RenamedObjectsRepo.class.getName();

    private List<Listener> listeners = new ArrayList<>();
    private Map<String, CityRegion> asMap = new HashMap<>();
    private List<CityRegion> asList = new ArrayList<>();

    public RenamedObjectsRepo() {
        /* TODO :
        *  once persisting implemented, enhance constructor with ability to
        *  read data from db.
        *  Constructor parameter should accept db instance then
        */
    }

    ///////////////////////////////////////////////////////////////////////////
    // Essential public methods
    ///////////////////////////////////////////////////////////////////////////

    public void setCityData(ApiDataHolder cityData) {
        asList = cityData.getRegionsAsList();
        asMap = ApiDataHolder.getRegionsAsMap(asList);
        pokeAttachedListeners();
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
    }

    private void pokeAttachedListeners() {
        checkListeners();
        Queryable.from(listeners).forEachR(Listener::onDataUpdated);
    }

    private void checkListeners() {
        if (listeners.isEmpty()) Log.w(TAG, "No attached listeners! Check your setup maybe?");
    }
}
