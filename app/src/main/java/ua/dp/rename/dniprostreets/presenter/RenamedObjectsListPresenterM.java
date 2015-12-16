package ua.dp.rename.dniprostreets.presenter;

import android.support.annotation.Nullable;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import timber.log.Timber;
import ua.dp.rename.dniprostreets.core.BasePresenter;
import ua.dp.rename.dniprostreets.entity.CityRegion;
import ua.dp.rename.dniprostreets.entity.RenamedObject;
import ua.dp.rename.dniprostreets.repo.RenamedObjectsRepo;
import ua.dp.rename.dniprostreets.rx.MainComposer;

public class RenamedObjectsListPresenterM extends BasePresenter<RenamedObjectsListPresenterM.View> {

    @Inject
    RenamedObjectsRepo dataRepo;

    private String id;
    private List<RenamedObject> dataSet;

    private SearchState searchState = SearchState.DATASET;

    public RenamedObjectsListPresenterM(@Nullable String id) {
        this.id = id;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (id == null) {
            getView().setTitle("SEARCH_TODO");
            dataSet = dataRepo.getAllRenamedObjects();
            getView().applyDataSet(dataSet);
        } else {
            CityRegion region = dataRepo.getRegion(id);
            if (region == null) {
                getView().showError("Error getting region by ID");
            } else {
                dataSet = region.getObjects();
                getView().applyDataSet(dataSet);
                getView().setTitle(region.getOldAreaName());
            }
        }
    }

    public void searchRequested(final String query) {
        if (query.isEmpty() && searchState.equals(SearchState.SEARCH)) {
            getView().applyDataSet(dataSet);
            searchState = SearchState.DATASET;
            return;
        }
        if (!query.isEmpty()) {
            Observable.from(dataSet)
                    .filter(renamedObject ->
                            matchesSearch(renamedObject.getNewName(), query)
                                    || matchesSearch(renamedObject.getOldName(), query))
                    .toList()
                    .compose(new MainComposer<>())
                    .subscribe(getView()::applyDataSet,
                            e -> {
                                Timber.e(e, "Error applying search results!");
                                getView().showError("Error during search");
                            });
            searchState = SearchState.SEARCH;
        }
    }

    private boolean matchesSearch(String superString, String query) {
        return superString.toLowerCase().contains(query.toLowerCase());
    }

    public interface View extends MvpView {

        void setTitle(String title);

        void applyDataSet(List<RenamedObject> dataSet);

        void showError(String message);
    }

    public enum SearchState {
        SEARCH, DATASET
    }
}
