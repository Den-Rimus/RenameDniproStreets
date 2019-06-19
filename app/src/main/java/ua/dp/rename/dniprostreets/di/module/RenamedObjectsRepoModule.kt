package ua.dp.rename.dniprostreets.di.module

import dagger.Binds
import dagger.Module
import ua.dp.rename.dniprostreets.repo.RenamedObjectsRepo
import ua.dp.rename.dniprostreets.repo.RenamedObjectsRepoImpl
import javax.inject.Singleton

@Module
interface RenamedObjectsRepoModule {

   @Binds
   @Singleton
   fun provideRenamedRepo(impl: RenamedObjectsRepoImpl): RenamedObjectsRepo
}
