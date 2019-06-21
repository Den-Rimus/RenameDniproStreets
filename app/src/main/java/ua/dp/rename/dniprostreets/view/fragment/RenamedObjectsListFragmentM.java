package ua.dp.rename.dniprostreets.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.jakewharton.rxbinding3.appcompat.RxSearchView;
import com.jakewharton.rxbinding3.appcompat.SearchViewQueryTextEvent;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import kotlin.Unit;
import ua.dp.rename.dniprostreets.App;
import ua.dp.rename.dniprostreets.R;
import ua.dp.rename.dniprostreets.adapter.RenamedObjectsAdapter;
import ua.dp.rename.dniprostreets.bundle.RenamedObjectsListBundle;
import ua.dp.rename.dniprostreets.core.BaseFragmentM;
import ua.dp.rename.dniprostreets.core.Layout;
import ua.dp.rename.dniprostreets.di.component.DaggerRegionDetailComponent;
import ua.dp.rename.dniprostreets.entity.RenamedObject;
import ua.dp.rename.dniprostreets.presenter.RenamedObjectsListPresenterM;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Layout(R.layout.fragment_renamed_objects_list)
public class RenamedObjectsListFragmentM
      extends BaseFragmentM<RenamedObjectsListPresenterM.View, RenamedObjectsListPresenterM>
      implements RenamedObjectsListPresenterM.View {

   private static final int DEBOUNCE_INTERVAL_LENGTH = 600;

   private RecyclerView recyclerView;
   private Toolbar toolbar;

   SearchView searchView;
   private boolean expandForGlobalSearch;

   private RenamedObjectsAdapter adapter;

   private Disposable searchViewDisposable;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setHasOptionsMenu(true);
   }

   @Override
   public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);

      recyclerView = view.findViewById(R.id.recyclerView);
      toolbar = view.findViewById(R.id.toolbar);

      DaggerRegionDetailComponent.builder()
            .appComponent(((App) getActivity().getApplication()).getAppComponent())
            .build().inject(presenter);

      expandForGlobalSearch = ((RenamedObjectsListBundle) getArgs()).getGlobalSearch();

      ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
      initToolbar(toolbar);

      adapter = new RenamedObjectsAdapter(this::onItemClicked);
      recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
      recyclerView.setAdapter(adapter);

      presenter.onStart();
   }

   private Unit onItemClicked(RenamedObject item) {
      presenter.onItemClicked(item);

      return Unit.INSTANCE;
   }

   @Override
   public void onResume() {
      super.onResume();
      if (searchViewDisposable != null && searchViewDisposable.isDisposed())
         subscribeSearch(searchView);
   }

   @Override
   public void onPause() {
      if (searchViewDisposable != null) searchViewDisposable.dispose();
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
      searchViewDisposable = RxSearchView.queryTextChangeEvents(searchView)
            .debounce(DEBOUNCE_INTERVAL_LENGTH, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onQueryTextChange, Throwable::printStackTrace);
   }

   private void onQueryTextChange(SearchViewQueryTextEvent event) {
      presenter.searchRequested(event.getQueryText().toString());
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
      Navigation.findNavController(getView())
            .navigate(R.id.action_navigate_to_renamed_details, prepareBundle(model));
   }

   @Override
   @NonNull
   public RenamedObjectsListPresenterM createPresenter() {
      return new RenamedObjectsListPresenterM(((RenamedObjectsListBundle) getArgs()).getId());
   }
}
