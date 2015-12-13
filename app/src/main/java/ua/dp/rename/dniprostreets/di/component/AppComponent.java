package ua.dp.rename.dniprostreets.di.component;

import javax.inject.Singleton;

import dagger.Component;
import ua.dp.rename.dniprostreets.App;
import ua.dp.rename.dniprostreets.di.module.ApiModule;
import ua.dp.rename.dniprostreets.di.module.AppModule;
import ua.dp.rename.dniprostreets.di.module.RenamedObjectsRepoModule;
import ua.dp.rename.dniprostreets.presenter.RegionsListPresenterM;
import ua.dp.rename.dniprostreets.presenter.RenamedObjectsListPresenterM;

@Singleton @Component(modules = {AppModule.class, ApiModule.class, RenamedObjectsRepoModule.class})
public interface AppComponent {
    void inject(App application);
    void inject(RegionsListPresenterM regionsListPresenterM);
    void inject(RenamedObjectsListPresenterM renamedObjectsListPresenterM);
}
