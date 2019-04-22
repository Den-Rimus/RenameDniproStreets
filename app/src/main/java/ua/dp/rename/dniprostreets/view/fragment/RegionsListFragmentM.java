package ua.dp.rename.dniprostreets.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ua.dp.rename.dniprostreets.App;
import ua.dp.rename.dniprostreets.R;
import ua.dp.rename.dniprostreets.adapter.CityRegionsAdapter;
import ua.dp.rename.dniprostreets.bundle.RenamedObjectsListBundle;
import ua.dp.rename.dniprostreets.core.BaseFragmentM;
import ua.dp.rename.dniprostreets.core.Layout;
import ua.dp.rename.dniprostreets.entity.CityRegion;
import ua.dp.rename.dniprostreets.presenter.RegionsListPresenterM;
import ua.dp.rename.dniprostreets.view.AboutActivity;
import ua.dp.rename.dniprostreets.view.DividerItemDecoration;

@Layout(R.layout.fragment_regions_list)
public class RegionsListFragmentM extends BaseFragmentM<RegionsListPresenterM.View, RegionsListPresenterM>
        implements RegionsListPresenterM.View {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private CityRegionsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);

        ((App) getActivity().getApplication()).getAppComponent().inject(this.presenter);

        final Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        adapter = new CityRegionsAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
        recyclerView.setAdapter(adapter);

        presenter.onStart();
    }

    @Override
    public void openRenamedObjectsList(RenamedObjectsListBundle args) {
        Navigation.findNavController(getView())
              .navigate(R.id.action_navigate_to_region_details, prepareBundle(args));
    }

    @Override
    public void onDataSetObtained(List<CityRegion> dataSet) {
        if (dataSet == null || dataSet.isEmpty()) return;
        progressBar.setVisibility(android.view.View.GONE);
        adapter.setAll(dataSet);
        recyclerView.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_regions_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search)
            presenter.openGlobalSearch();
        if (item.getItemId() == R.id.action_about)
            startAboutActivity();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showError() {
        informUser(R.string.general_error_snack_caption);
        progressBar.setVisibility(android.view.View.GONE);
    }

    @Override @NonNull
    public RegionsListPresenterM createPresenter() {
        return new RegionsListPresenterM();
    }

    private void startAboutActivity() {
        startActivity(new Intent(getContext(), AboutActivity.class));
    }
}
