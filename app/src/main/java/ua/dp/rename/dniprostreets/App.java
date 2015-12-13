package ua.dp.rename.dniprostreets;

import android.app.Application;

import timber.log.Timber;
import ua.dp.rename.dniprostreets.di.component.AppComponent;
import ua.dp.rename.dniprostreets.di.component.DaggerAppComponent;
import ua.dp.rename.dniprostreets.di.module.ApiModule;
import ua.dp.rename.dniprostreets.di.module.AppModule;
import ua.dp.rename.dniprostreets.di.module.RenamedObjectsRepoModule;

public class App extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .apiModule(new ApiModule())
                .renamedObjectsRepoModule(new RenamedObjectsRepoModule())
                .build();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public AppComponent component() {
        return component;
    }
}
