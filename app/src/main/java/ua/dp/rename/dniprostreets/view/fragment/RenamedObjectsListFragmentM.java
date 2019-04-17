package ua.dp.rename.dniprostreets.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;
import com.jakewharton.rxbinding.support.v7.widget.SearchViewQueryTextEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Subscription;
import ua.dp.rename.dniprostreets.App;
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

   @BindView(R.id.recyclerView) RecyclerView recyclerView;
   @BindView(R.id.toolbar) Toolbar toolbar;

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
      ((App) getActivity().getApplication()).component().inject(this.presenter);

      expandForGlobalSearch = ((RenamedObjectsListBundle) getArgs()).isGlobalSearch();

      ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
      initToolbar(toolbar);

      adapter = new RenamedObjectsAdapter(getActivity());
      recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
      recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
      recyclerView.setAdapter(adapter);

      presenter.onStart();
   }

   @Override
   public void onResume() {
      super.onResume();
      if (searchViewSubscription != null && searchViewSubscription.isUnsubscribed())
         subscribeSearch(searchView);
   }

   @Override
   public void onPause() {
      if (searchViewSubscription != null) searchViewSubscription.unsubscribe();
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
      toolbar.setNavigationIcon(R.drawable.back_icon);
      toolbar.setNavigationOnClickListener(view -> getActivity().onBackPressed());
   }

   private void initSearch(MenuItem menuItem) {
      searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
      searchView.setMaxWidth(Integer.MAX_VALUE);
      searchView.setOnCloseListener(() -> {
         tryHideSoftInput();
         return false;
      });
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
   public void setTitle(String title) {
      toolbar.setTitle(title);
   }

   @Override
   public void setTitle(@StringRes int titleResId) {
      toolbar.setTitle(titleResId);
   }

   @Override
   public void showError(String message) {
      informUser(message);
   }

   @Override
   public void applyDataSet(List<RenamedObject> dataSet) {
      adapter.setAll(dataSet);
   }

   @Override
   public void openDetails(RenamedObject model) {
      getFragmentManager().beginTransaction().replace(R.id.main_container,
            DetailsFragment.instantiate(getContext(), DetailsFragment.class.getName(), model))
            .addToBackStack(DetailsFragment.class.getName())
            .commit();
   }

   @Override
   @NonNull
   public RenamedObjectsListPresenterM createPresenter() {
      return new RenamedObjectsListPresenterM(((RenamedObjectsListBundle) getArgs()).getId());
   }
}
