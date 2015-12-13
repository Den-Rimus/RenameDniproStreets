package ua.dp.rename.dniprostreets.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ua.dp.rename.dniprostreets.repo.RenamedObjectsRepo;

@Module
public class RenamedObjectsRepoModule {

    @Provides @Singleton
    RenamedObjectsRepo provideRenamedObjectsRepo() {
        return new RenamedObjectsRepo();
    }
}
