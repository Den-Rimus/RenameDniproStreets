package ua.dp.rename.dniprostreets.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;
import com.jakewharton.rxbinding.support.v7.widget.SearchViewQueryTextEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import rx.Subscription;
import ua.dp.rename.dniprostreets.R;
import ua.dp.rename.dniprostreets.adapter.RenamedObjectsAdapter;
import ua.dp.rename.dniprostreets.bundle.RenamedObjectsListBundle;
import ua.dp.rename.dniprostreets.core.BaseFragmentM;
import ua.dp.rename.dniprostreets.core.Layout;
import ua.dp.rename.dniprostreets.entity.RenamedObject;
import ua.dp.rename.dniprostreets.presenter.RenamedObjectsListPresenterM;
import ua.dp.rename.dniprostreets.rx.MainComposer;
import ua.dp.rename.dniprostreets.view.DividerItemDecoration;

@Layout(R.layout.fragment_renamed_objects_list)
public class RenamedObjectsListFragmentM
        extends BaseFragmentM<RenamedObjectsListPresenterM.View, RenamedObjectsListPresenterM>
        implements RenamedObjectsListPresenterM.View {

    private static final int DEBOUNCE_INTERVAL_LENGTH = 600;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    SearchView searchView;
    private boolean expandForGlobalSearch;

    private RenamedObjectsAdapter adapter;

    private Subscription searchViewSubscription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RenamedObjectsListBundle args = (RenamedObjectsListBundle) getArgs();

        expandForGlobalSearch = args.isGlobalSearch();

        toolbar.setTitle(args.getTitle());
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        adapter = new RenamedObjectsAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setAll(args.getDataSet());

        initToolbar(toolbar);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (searchViewSubscription != null && searchViewSubscription.isUnsubscribed())
            subscribeSearch(searchView);
    }

    @Override
    public void onPause() {
        searchViewSubscription.unsubscribe();
        super.onPause();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_renamed_objects_menu, menu);
        initSearch(menu.findItem(R.id.action_search));
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (expandForGlobalSearch) searchView.setIconified(false);
    }

    @SuppressLint("PrivateResource")
    private void initToolbar(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(view -> getActivity().onBackPressed());
    }

    private void initSearch(MenuItem menuItem) {
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnCloseListener(() -> false);
        searchView.setQueryHint(getString(R.string.menu_item_search_hint));
        searchView.setSubmitButtonEnabled(false);
        searchView.setQuery(null, false);

        subscribeSearch(searchView);
    }

    private void subscribeSearch(SearchView searchView) {
        searchViewSubscription = RxSearchView.queryTextChangeEvents(searchView)
                .debounce(DEBOUNCE_INTERVAL_LENGTH, TimeUnit.MILLISECONDS)
                .compose(new MainComposer<>())
                .subscribe(this::onQueryTextChange, Throwable::printStackTrace);
    }

    private void onQueryTextChange(SearchViewQueryTextEvent event) {
        presenter.searchRequested(event.queryText().toString());
    }

    @Override
    public void applyDataSet(List<RenamedObject> dataSet) {
        adapter.setAll(dataSet);
    }

    @Override @NonNull
    public RenamedObjectsListPresenterM createPresenter() {
        return new RenamedObjectsListPresenterM(((RenamedObjectsListBundle) getArgs()).getDataSet());
    }
}
