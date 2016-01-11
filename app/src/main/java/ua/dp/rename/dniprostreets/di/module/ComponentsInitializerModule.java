package ua.dp.rename.dniprostreets.di.module;

import dagger.Module;
import dagger.Provides;
import ua.dp.rename.dniprostreets.core.ComponentInitializer;
import ua.dp.rename.dniprostreets.core.LogInitializer;
import ua.dp.rename.dniprostreets.core.SnappyInitializer;
import ua.dp.rename.dniprostreets.repo.SnappyRepository;

@Module
public class ComponentsInitializerModule {

    @Provides(type = Provides.Type.SET)
    ComponentInitializer provideSnappyInitializer(SnappyRepository db) {
        return new SnappyInitializer(db);
    }

    @Provides(type = Provides.Type.SET)
    ComponentInitializer provideLogInitializer() {
        return new LogInitializer();
    }
}
