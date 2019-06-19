package ua.dp.rename.dniprostreets.di.component

import dagger.Component
import ua.dp.rename.dniprostreets.App
import ua.dp.rename.dniprostreets.di.module.ApiModule
import ua.dp.rename.dniprostreets.di.module.AppModule
import ua.dp.rename.dniprostreets.di.module.ComponentsInitializerModule
import ua.dp.rename.dniprostreets.di.module.RenamedObjectsRepoModule
import ua.dp.rename.dniprostreets.repo.RenamedObjectsRepo
import javax.inject.Singleton

@Singleton
@Component(modules = [
   AppModule::class,
   ComponentsInitializerModule::class,
   RenamedObjectsRepoModule::class,
   ApiModule::class
])
interface AppComponent {

   fun inject(app: App)

   fun renamedRepo(): RenamedObjectsRepo
}
