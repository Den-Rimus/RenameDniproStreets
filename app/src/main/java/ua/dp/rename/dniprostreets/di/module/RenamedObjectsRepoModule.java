package ua.dp.rename.dniprostreets.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ua.dp.rename.dniprostreets.di.annotation.PerApplication;
import ua.dp.rename.dniprostreets.repo.RenamedObjectsRepo;

@Module
public class RenamedObjectsRepoModule {

    @Provides @PerApplication @Singleton
    RenamedObjectsRepo provideRenamedObjectsRepo() {
        return new RenamedObjectsRepo();
    }
}
