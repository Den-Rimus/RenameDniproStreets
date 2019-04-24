package ua.dp.rename.dniprostreets.api

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import ua.dp.rename.dniprostreets.entity.ApiDataHolder
import ua.dp.rename.dniprostreets.entity.LastUpdateHolder

interface RenamedObjectsService {

   @GET("rename.json")
   fun getJson(): Single<Response<ApiDataHolder>>

   @GET("last_data_update.json")
   fun getLastDataUpdateTimestamp(): Single<Response<LastUpdateHolder>>
}
