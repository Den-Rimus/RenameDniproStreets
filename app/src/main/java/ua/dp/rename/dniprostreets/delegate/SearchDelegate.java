package ua.dp.rename.dniprostreets.delegate;

import android.support.v7.widget.SearchView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rx.Subscription;
import rx.functions.Func1;

public class SearchDelegate<T> {

    private static final String TAG = SearchDelegate.class.getName();

    private static final int DEBOUNCE_INTERVAL_LENGTH = 600;

    private List<T> searchedDataSet;
    private State state;
    private SearchAppliedListener appliedListener;
    private SearchClearedListener clearedListener;
    private Subscription searchViewSubscription;
    private SearchView searchView;
    private Func1<? super T, Boolean> predicate;

    private SearchDelegate(SearchView searchView, Collection superSet,
                           Func1<? super T, Boolean> predicate,
                           SearchAppliedListener appliedListener,
                           SearchClearedListener clearedListener) {
        this.state = State.NORMAL;
        this.searchView = searchView;
        this.searchedDataSet = new ArrayList<>(superSet);
        this.predicate = predicate;
        this.appliedListener = appliedListener;
        this.clearedListener = clearedListener;
        init();
    }

    private void init() {
//        searchViewSubscription = RxSearchView.queryTextChangeEvents(searchView)
//                .debounce(DEBOUNCE_INTERVAL_LENGTH, TimeUnit.MILLISECONDS)
//                .compose(new IoToMainComposer<>())
//                .subscribe(appliedListener::onSearchApplied, Throwable::printStackTrace);
    }

    public void onResume() {
        //
    }

    public void onPause() {
        //
    }

    private enum State {
        SEARCH_APPLIED, NORMAL
    }

    public interface SearchAppliedListener {
        void onSearchApplied(List filteredDataSet);
    }

    public interface SearchClearedListener {
        void onSearchCleared();
    }
}
