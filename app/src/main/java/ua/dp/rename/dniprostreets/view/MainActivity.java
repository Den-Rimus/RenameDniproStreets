package ua.dp.rename.dniprostreets.view;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ua.dp.rename.dniprostreets.R;
import ua.dp.rename.dniprostreets.view.fragment.RegionsListFragmentM;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getSupportFragmentManager().findFragmentById(R.id.main_container) == null)
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,
                RegionsListFragmentM.instantiate(this, RegionsListFragmentM.class.getName())).commit();
    }
}
