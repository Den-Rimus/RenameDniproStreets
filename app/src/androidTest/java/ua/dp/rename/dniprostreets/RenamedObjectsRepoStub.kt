package ua.dp.rename.dniprostreets

import ua.dp.rename.dniprostreets.entity.CityRegion
import ua.dp.rename.dniprostreets.entity.RenamedObject
import ua.dp.rename.dniprostreets.repo.RenamedObjectsRepo

class RenamedObjectsRepoStub : RenamedObjectsRepo {

   private val listeners = mutableListOf<RenamedObjectsRepo.Listener>() // TODO: bad design - re-do

   override fun requestUpdate() {
      listeners.forEach { it.onDataUpdated() }
   }

   override fun getRegions(): List<CityRegion> {
      return regionsList
   }

   override fun getRegion(id: String): CityRegion? {
      return regionsList.firstOrNull { it.id == id }
   }

   override fun getAllRenamedObjects(): List<RenamedObject> {
      return allRenamedObjectsList
   }

   override fun attachListener(listener: RenamedObjectsRepo.Listener) {
      listeners.add(listener) // TODO: bad design - re-do
   }

   override fun detachListener(listener: RenamedObjectsRepo.Listener) {
      listeners.remove(listener) // TODO: bad design - re-do
   }
}
