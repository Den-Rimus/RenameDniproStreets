package ua.dp.rename.dniprostreets

import ua.dp.rename.dniprostreets.di.module.RenamedObjectsRepoModule
import ua.dp.rename.dniprostreets.repo.RenamedObjectsRepo
import ua.dp.rename.dniprostreets.repo.RenamedObjectsRepoImpl

class RenamedObjectsRepoModuleMock : RenamedObjectsRepoModule() {

   override fun provideRenamedRepo(impl: RenamedObjectsRepoImpl): RenamedObjectsRepo {
      return RenamedObjectsRepoStub()
   }
}
