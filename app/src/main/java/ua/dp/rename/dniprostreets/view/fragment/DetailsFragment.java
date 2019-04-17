package ua.dp.rename.dniprostreets.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import ua.dp.rename.dniprostreets.R;
import ua.dp.rename.dniprostreets.core.BaseFragmentM;
import ua.dp.rename.dniprostreets.core.Layout;
import ua.dp.rename.dniprostreets.entity.RenamedObject;
import ua.dp.rename.dniprostreets.presenter.DetailsPresenter;

@Layout(R.layout.fragment_detail)
public class DetailsFragment extends BaseFragmentM<DetailsPresenter.View, DetailsPresenter>
      implements DetailsPresenter.View {

   private TextView oldName;
   private TextView newName;
   private Button details;
   private Toolbar toolbar;
   private TextView regionNameCaption;

   @Override
   public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);

      oldName = view.findViewById(R.id.oldName);
      newName = view.findViewById(R.id.newName);
      details = view.findViewById(R.id.details);
      toolbar = view.findViewById(R.id.toolbar);
      regionNameCaption = view.findViewById(R.id.regionName);
      details.setOnClickListener(v -> presenter.detailsClicked());

      ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
      initToolbar(toolbar);

      presenter.onStart();
   }

   @Override
   public void syncUI(RenamedObject model) {
      oldName.setText(model.getOldName());
      newName.setText(model.getNewName());
      details.setVisibility(model.hasLink() ? View.VISIBLE : View.GONE);

      final String regionName;
      if (model.getRegionOldName().equals(model.getRegionNewName())) {
         regionName = model.getRegionNewName();
      } else {
         regionName = String.format(Locale.US, getString(R.string.details_region_format), model.getRegionNewName(),
               model.getRegionOldName());
      }
      regionNameCaption.setText(regionName);
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
