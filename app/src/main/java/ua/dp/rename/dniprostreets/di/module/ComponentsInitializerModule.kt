package ua.dp.rename.dniprostreets.di.module

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import ua.dp.rename.dniprostreets.core.ComponentInitializer
import ua.dp.rename.dniprostreets.core.LogInitializer
import ua.dp.rename.dniprostreets.core.SnappyInitializer
import ua.dp.rename.dniprostreets.repo.SnappyRepository

@Module
open class ComponentsInitializerModule {

    @Provides
    @IntoSet
    open fun provideSnappyInitializer(snappyRepository: SnappyRepository): ComponentInitializer {
        return SnappyInitializer(snappyRepository)
    }

    @Provides
    @IntoSet
    open fun provideLogInitializer(): ComponentInitializer {
        return LogInitializer()
    }
}
