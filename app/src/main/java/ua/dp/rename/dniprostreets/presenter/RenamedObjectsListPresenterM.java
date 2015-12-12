package ua.dp.rename.dniprostreets.presenter;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

import rx.Observable;
import ua.dp.rename.dniprostreets.core.BasePresenter;
import ua.dp.rename.dniprostreets.entity.RenamedObject;
import ua.dp.rename.dniprostreets.rx.MainComposer;

public class RenamedObjectsListPresenterM extends BasePresenter<RenamedObjectsListPresenterM.View> {

    private List<RenamedObject> dataSet;

    private SearchState searchState = SearchState.DATASET;

    public RenamedObjectsListPresenterM(List<RenamedObject> dataSet) {
        this.dataSet = dataSet;
    }

    public void searchRequested(String query) {
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
                            e -> Log.e("Subscription", "onQueryTextChange " + e.toString()));
            searchState = SearchState.SEARCH;
        }
    }

    private boolean matchesSearch(String superString, String query) {
        return superString.toLowerCase().contains(query.toLowerCase());
    }

    public interface View extends MvpView {

        void applyDataSet(List<RenamedObject> dataSet);
    }

    public enum SearchState {
        SEARCH, DATASET
    }
}
