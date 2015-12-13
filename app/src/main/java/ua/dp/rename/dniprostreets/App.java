package ua.dp.rename.dniprostreets;

import android.app.Application;

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
    }

    public AppComponent component() {
        return component;
    }
}
