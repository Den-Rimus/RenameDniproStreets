package ua.dp.rename.dniprostreets

import ua.dp.rename.dniprostreets.core.ComponentInitializer
import ua.dp.rename.dniprostreets.di.module.ComponentsInitializerModule
import ua.dp.rename.dniprostreets.repo.SnappyRepository

class ComponentsInitializerModuleMock : ComponentsInitializerModule() {

   override fun provideSnappyInitializer(snappyRepository: SnappyRepository): ComponentInitializer {
      return MockInitializer()
   }

   override fun provideLogInitializer(): ComponentInitializer {
      return MockInitializer()
   }
}
