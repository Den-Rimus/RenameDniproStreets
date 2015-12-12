package ua.dp.rename.dniprostreets.presenter;

import android.support.annotation.Nullable;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.innahema.collections.query.queriables.Queryable;

import java.util.ArrayList;
import java.util.List;

import ua.dp.rename.dniprostreets.App;
import ua.dp.rename.dniprostreets.bundle.RenamedObjectsListBundle;
import ua.dp.rename.dniprostreets.core.BasePresenter;
import ua.dp.rename.dniprostreets.entity.ApiDataHolder;
import ua.dp.rename.dniprostreets.entity.CityRegion;
import ua.dp.rename.dniprostreets.entity.RenamedObject;
import ua.dp.rename.dniprostreets.event.CityRegionClickedEvent;
import ua.dp.rename.dniprostreets.rx.IoToMainComposer;

public class RegionsListPresenterM extends BasePresenter<RegionsListPresenterM.View> {

    private List<CityRegion> dataSet = new ArrayList<>();

    public void requestDataSet(boolean forceUpdate) {
        if (forceUpdate || dataSet.isEmpty())
            App.getApiService().getJson()
                    .map(ApiDataHolder::getRegionsAsList)
                    .compose(new IoToMainComposer<>())
                    .subscribe(this::dataSetObtained);
        else
            dataSetObtained(null);
    }

    private void dataSetObtained(@Nullable List<CityRegion> newDataSet) {
        if (newDataSet == null)
            getView().onDataSetObtained(dataSet);
        else
            getView().onDataSetObtained(newDataSet);
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(final CityRegionClickedEvent event) {
        getView().openRenamedObjectsList(new RenamedObjectsListBundle(event.cityRegion));
    }

    public void openGlobalSearch() {
        final List<RenamedObject> superSet = new ArrayList<>();
        Queryable.from(dataSet).forEachR(region -> superSet.addAll(region.getObjects()));
        getView().openRenamedObjectsList(new RenamedObjectsListBundle("TODO",
                Queryable.from(superSet).sort(RenamedObject.ALPHABETICAL_COMPARATOR)
                        .distinct().toList()));
    }

    public interface View extends MvpView {

        void openRenamedObjectsList(RenamedObjectsListBundle args);

        void onDataSetObtained(List<CityRegion> dataSet);
    }
}
