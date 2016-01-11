package ua.dp.rename.dniprostreets;

import android.app.Application;

import com.innahema.collections.query.queriables.Queryable;

import java.util.Set;

import javax.inject.Inject;

import ua.dp.rename.dniprostreets.core.ComponentInitializer;
import ua.dp.rename.dniprostreets.di.component.AppComponent;
import ua.dp.rename.dniprostreets.di.component.DaggerAppComponent;
import ua.dp.rename.dniprostreets.di.module.ApiModule;
import ua.dp.rename.dniprostreets.di.module.AppModule;
import ua.dp.rename.dniprostreets.di.module.ComponentsInitializerModule;
import ua.dp.rename.dniprostreets.di.module.RenamedObjectsRepoModule;

public class App extends Application {

    private AppComponent component;
    //
    @Inject
    Set<ComponentInitializer> initializers;

    @Override
    public void onCreate() {
        super.onCreate();
        //
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .apiModule(new ApiModule())
                .renamedObjectsRepoModule(new RenamedObjectsRepoModule())
                .componentsInitializerModule(new ComponentsInitializerModule())
                .build();
        //
        component.inject(this);
        //
        initComponents();
    }

    private void initComponents() {
        Queryable.from(initializers).forEachR(ComponentInitializer::init);
    }

    public AppComponent component() {
        return component;
    }
}
