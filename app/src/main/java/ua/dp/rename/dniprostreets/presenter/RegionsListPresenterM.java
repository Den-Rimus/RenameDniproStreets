package ua.dp.rename.dniprostreets.presenter;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

import javax.inject.Inject;

import ua.dp.rename.dniprostreets.bundle.RenamedObjectsListBundle;
import ua.dp.rename.dniprostreets.core.BasePresenter;
import ua.dp.rename.dniprostreets.entity.CityRegion;
import ua.dp.rename.dniprostreets.event.CityRegionClickedEvent;
import ua.dp.rename.dniprostreets.repo.RenamedObjectsRepo;

public class RegionsListPresenterM extends BasePresenter<RegionsListPresenterM.View>
        implements RenamedObjectsRepo.Listener {

    @Inject
    RenamedObjectsRepo dataRepo;

    @Override
    public void onStart() {
        super.onStart();
        dataRepo.attachListener(this);
        getView().onDataSetObtained(dataRepo.getRegionsAsList());
        dataRepo.requestUpdate();
    }

    @Override
    public void onDataUpdated() {
        getView().onDataSetObtained(dataRepo.getRegionsAsList());
    }

    @Override
    public void onDataRequestError() {
        getView().showError();
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(final CityRegionClickedEvent event) {
        getView().openRenamedObjectsList(new RenamedObjectsListBundle(event.cityRegion.getId()));
    }

    public void openGlobalSearch() {
        getView().openRenamedObjectsList(new RenamedObjectsListBundle());
    }

    @Override
    public void detachView(boolean retainInstance) {
        dataRepo.detachListener(this);
        super.detachView(retainInstance);
    }

    public interface View extends MvpView {

        void openRenamedObjectsList(RenamedObjectsListBundle args);

        void onDataSetObtained(List<CityRegion> dataSet);

        void showError();
    }
}
