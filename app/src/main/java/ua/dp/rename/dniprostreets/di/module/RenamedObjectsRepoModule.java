package ua.dp.rename.dniprostreets.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ua.dp.rename.dniprostreets.api.RenamedObjectsService;
import ua.dp.rename.dniprostreets.repo.RenamedObjectsRepo;
import ua.dp.rename.dniprostreets.repo.SnappyRepository;

@Module
public class RenamedObjectsRepoModule {

    @Provides @Singleton
    RenamedObjectsRepo provideRenamedObjectsRepo(SnappyRepository db, RenamedObjectsService apiService) {
        return new RenamedObjectsRepo(db, apiService);
    }
}
