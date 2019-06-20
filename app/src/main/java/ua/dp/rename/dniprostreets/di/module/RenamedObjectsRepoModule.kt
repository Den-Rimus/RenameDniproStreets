package ua.dp.rename.dniprostreets.di.module

import dagger.Module
import dagger.Provides
import ua.dp.rename.dniprostreets.repo.RenamedObjectsRepo
import ua.dp.rename.dniprostreets.repo.RenamedObjectsRepoImpl
import javax.inject.Singleton

@Module
open class RenamedObjectsRepoModule {

   @Provides
   @Singleton
   open fun provideRenamedRepo(impl: RenamedObjectsRepoImpl): RenamedObjectsRepo = impl
}
