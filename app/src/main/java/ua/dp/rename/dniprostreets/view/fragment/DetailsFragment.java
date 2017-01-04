package ua.dp.rename.dniprostreets.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import ua.dp.rename.dniprostreets.R;
import ua.dp.rename.dniprostreets.core.BaseFragmentM;
import ua.dp.rename.dniprostreets.core.Layout;
import ua.dp.rename.dniprostreets.entity.RenamedObject;
import ua.dp.rename.dniprostreets.presenter.DetailsPresenter;

@Layout(R.layout.fragment_detail)
public class DetailsFragment extends BaseFragmentM<DetailsPresenter.View, DetailsPresenter>
      implements DetailsPresenter.View {

   @Bind(R.id.oldName) TextView oldName;
   @Bind(R.id.newName) TextView newName;
   @Bind(R.id.details) Button details;
   @Bind(R.id.toolbar) Toolbar toolbar;

   @Override
   public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);

      ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
      initToolbar(toolbar);

      presenter.onStart();
   }

   @Override
   public void syncUI(RenamedObject model) {
      oldName.setText(model.getOldName());
      newName.setText(model.getNewName());
      details.setVisibility(model.hasLink() ? View.VISIBLE : View.GONE);
   }

   @OnClick(R.id.details)
   void detailsClicked() {
      presenter.detailsClicked();
   }

   @Override
   public void showDetails(String pageUrl) {
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(pageUrl)));
   }

   @SuppressLint("PrivateResource")
   private void initToolbar(Toolbar toolbar) {
      toolbar.setNavigationIcon(R.drawable.back_icon);
      toolbar.setNavigationOnClickListener(view -> getActivity().onBackPressed());
      toolbar.setTitle(R.string.details_screen_title);
   }

   @Override
   @NonNull
   public DetailsPresenter createPresenter() {
      return new DetailsPresenter((RenamedObject) getArgs());
   }
}
