package ua.dp.rename.dniprostreets.repo

import ua.dp.rename.dniprostreets.entity.CityRegion
import ua.dp.rename.dniprostreets.entity.RenamedObject

interface RenamedObjectsRepo {

   fun requestUpdate()

   fun getRegions(): List<CityRegion>

   fun getRegion(id: String): CityRegion?

   fun getAllRenamedObjects(): List<RenamedObject>

   interface Listener {

      fun onDataUpdated()

      fun onDataRequestError()
   }

   fun attachListener(listener: Listener)

   fun detachListener(listener: Listener)
}
