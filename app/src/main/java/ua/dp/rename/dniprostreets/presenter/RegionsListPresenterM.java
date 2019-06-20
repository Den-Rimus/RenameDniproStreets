package ua.dp.rename.dniprostreets.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpView;
import ua.dp.rename.dniprostreets.bundle.RenamedObjectsListBundle;
import ua.dp.rename.dniprostreets.core.BasePresenter;
import ua.dp.rename.dniprostreets.entity.CityRegion;
import ua.dp.rename.dniprostreets.repo.RenamedObjectsRepo;

import javax.inject.Inject;
import java.util.List;

public class RegionsListPresenterM extends BasePresenter<RegionsListPresenterM.View>
        implements RenamedObjectsRepo.Listener {

    @Inject
    RenamedObjectsRepo dataRepo;

    @Override
    public void onStart() {
        super.onStart();
        dataRepo.attachListener(this);
        getView().onDataSetObtained(dataRepo.getRegions());
        dataRepo.requestUpdate();
    }

    @Override
    public void onDataUpdated() {
        getView().onDataSetObtained(dataRepo.getRegions());
    }

    @Override
    public void onDataRequestError() {
        getView().showError();
    }

    public void onItemClicked(CityRegion item) {
        getView().openRenamedObjectsList(new RenamedObjectsListBundle(item.getId()));
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
