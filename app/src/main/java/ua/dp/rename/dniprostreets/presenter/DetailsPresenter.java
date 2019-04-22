package ua.dp.rename.dniprostreets.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import ua.dp.rename.dniprostreets.core.BasePresenter;
import ua.dp.rename.dniprostreets.entity.RenamedObject;

public class DetailsPresenter extends BasePresenter<DetailsPresenter.View> {

   private RenamedObject model;

   public DetailsPresenter(RenamedObject model) {
      this.model = model;
   }

   @Override
   public void onStart() {
      super.onStart();
      getView().syncUI(model);
   }

   public void detailsClicked() {
      if (!model.hasLink()) return; // for safety

      String url = model.getLink().getUrl();
      if (!url.startsWith("http://") && !url.startsWith("https://")) url = "http://" + url;

      getView().showDetails(url);
   }

   public interface View extends MvpView {

      void syncUI(RenamedObject model);

      void showDetails(String pageUrl);
   }
}
