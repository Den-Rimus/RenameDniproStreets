package ua.dp.rename.dniprostreets.presenter;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import io.reactivex.Observable;
import timber.log.Timber;
import ua.dp.rename.dniprostreets.R;
import ua.dp.rename.dniprostreets.core.BasePresenter;
import ua.dp.rename.dniprostreets.entity.CityRegion;
import ua.dp.rename.dniprostreets.entity.RenamedObject;
import ua.dp.rename.dniprostreets.repo.RenamedObjectsRepo;

import javax.inject.Inject;
import java.util.List;

public class RenamedObjectsListPresenterM extends BasePresenter<RenamedObjectsListPresenterM.View> {

   @Inject
   RenamedObjectsRepo dataRepo;

   private String id;
   private CityRegion cityRegion;
   private List<RenamedObject> dataSet;

   private SearchState searchState = SearchState.DATASET;

   public RenamedObjectsListPresenterM(@Nullable String id) {
      this.id = id;
   }

   @Override
   public void onStart() {
      super.onStart();
      if (id == null) {
         getView().setTitle((R.string.screen_title_search));
         dataSet = dataRepo.getAllRenamedObjects();
         getView().applyDataSet(dataSet);
      } else {
         cityRegion = dataRepo.getRegion(id);
         if (cityRegion == null) {
            getView().showError("Error getting region by ID");
         } else {
            dataSet = cityRegion.getObjects();
            getView().applyDataSet(dataSet);
            getView().setTitle(cityRegion.getOldAreaName());
         }
      }
   }

   public void onItemClicked(RenamedObject item) {
      if (cityRegion != null) {
         item.setRegionNewName(cityRegion.getNewAreaName());
         item.setRegionOldName(cityRegion.getOldAreaName());
      }
      getView().openDetails(item);
   }

   public void searchRequested(final String query) {
      if (query.isEmpty() && searchState.equals(SearchState.SEARCH)) {
         getView().applyDataSet(dataSet);
         searchState = SearchState.DATASET;
         return;
      }
      if (!query.isEmpty()) {
         Observable.fromIterable(dataSet)
               .filter(renamedObject ->
                     matchesSearch(renamedObject.getNewName(), query)
                           || matchesSearch(renamedObject.getOldName(), query))
               .toList()
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

      void setTitle(@StringRes int titleResId);

      void applyDataSet(List<RenamedObject> dataSet);

      void showError(String message);

      void openDetails(RenamedObject model);
   }

   public enum SearchState {
      SEARCH, DATASET
   }
}
