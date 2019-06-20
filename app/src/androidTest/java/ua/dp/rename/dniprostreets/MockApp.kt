package ua.dp.rename.dniprostreets

import ua.dp.rename.dniprostreets.di.component.AppComponent
import ua.dp.rename.dniprostreets.di.component.DaggerAppComponent
import ua.dp.rename.dniprostreets.di.module.AppModule

class MockApp : App() {

   override fun createComponent(): AppComponent {
      return DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .renamedObjectsRepoModule(RenamedObjectsRepoModuleMock())
            .componentsInitializerModule(ComponentsInitializerModuleMock())
            .build()
   }
}
