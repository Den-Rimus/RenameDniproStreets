package ua.dp.rename.dniprostreets.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.dp.rename.dniprostreets.R;
import ua.dp.rename.dniprostreets.util.RotationUtil;

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        RotationUtil.lockScreenOrientation(this);
        toolbar.setTitle(R.string.screen_title_about);
        toolbar.setNavigationIcon(R.drawable.back_icon);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }
}
