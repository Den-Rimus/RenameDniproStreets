package ua.dp.rename.dniprostreets.repo

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import ua.dp.rename.dniprostreets.api.RenamedObjectsService
import ua.dp.rename.dniprostreets.entity.ApiDataHolder
import ua.dp.rename.dniprostreets.entity.CityRegion
import ua.dp.rename.dniprostreets.entity.LastUpdateHolder
import ua.dp.rename.dniprostreets.entity.RenamedObject
import ua.dp.rename.dniprostreets.rx.mapSafeResponse

interface RenamedObjectsRepo {

   fun requestUpdate()

   fun getRegionsAsList(): List<CityRegion>

   fun getRegion(id: String): CityRegion?

   fun getAllRenamedObjects(): List<RenamedObject>

   interface Listener {

      fun onDataUpdated()

      fun onDataRequestError()
   }

   fun attachListener(listener: Listener)

   fun detachListener(listener: Listener)
}

class RenamedObjectsRepoImpl(
      private val db: SnappyRepository,
      private val apiService: RenamedObjectsService
) : RenamedObjectsRepo {

   private val listeners = mutableListOf<RenamedObjectsRepo.Listener>()
   private val asList = mutableListOf<CityRegion>()
   private val asMap = mutableMapOf<String, CityRegion>()

   init {
      asList.addAll(db.cityRegions)
      asMap.putAll(ApiDataHolder.getRegionsAsMap(asList))
   }

   override fun requestUpdate() {
      apiService.getLastDataUpdateTimestamp()
            .mapSafeResponse()
            .map(LastUpdateHolder::lastUpdate)
            .filter(this::updateNeeded)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ performUpdate() }, {
               Timber.e(it, "API error")
               pokeAttachedListenersWithError()
            })
   }

   override fun getRegionsAsList(): List<CityRegion> {
      return asList
   }

   override fun getRegion(id: String): CityRegion? {
      return asMap[id]
   }

   override fun getAllRenamedObjects(): List<RenamedObject> {
      val superSet = mutableListOf<RenamedObject>()
      asList.forEach { region ->
         superSet.addAll(region.objects.map {
            it.apply {
               regionOldName = region.oldAreaName
               regionNewName = region.newAreaName
            }
         })
      }
      superSet.sortWith(ALPHABETICAL_COMPARATOR)
      return superSet
   }

   private val ALPHABETICAL_COMPARATOR: Comparator<RenamedObject> = Comparator { lhs, rhs ->
      lhs.newName.toLowerCase().compareTo(rhs.newName.toLowerCase())
   }

   private fun updateNeeded(lastUpdate: Long): Boolean {
      return db.lastUpdateTimestamp < lastUpdate
   }

   private fun performUpdate() {
      apiService.getJson()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .mapSafeResponse()
            .subscribe({ dataSetObtained(it) }, { e ->
               pokeAttachedListenersWithError()
               Timber.e(e, "API error")
            })
   }

   private fun dataSetObtained(cityData: ApiDataHolder) {
      asList.apply {
         clear()
         addAll(cityData.getRegionsAsList())
      }
      asMap.apply {
         clear()
         putAll(ApiDataHolder.getRegionsAsMap(asList))
      }
      pokeAttachedListenersWithSuccess()
      db.putCityRegions(asList)
      db.lastUpdateTimestamp = System.currentTimeMillis()
   }

   override fun attachListener(listener: RenamedObjectsRepo.Listener) {
      listeners.add(listener)
   }

   override fun detachListener(listener: RenamedObjectsRepo.Listener) {
      listeners.remove(listener)
   }

   private fun pokeAttachedListenersWithSuccess() {
      checkListeners()
      listeners.forEach { it.onDataUpdated() }
   }

   private fun pokeAttachedListenersWithError() {
      checkListeners()
      listeners.forEach { it.onDataRequestError() }
   }

   private fun checkListeners() {
      if (listeners.isEmpty()) Timber.w("No attached listeners! Check your setup maybe?")
   }
}
