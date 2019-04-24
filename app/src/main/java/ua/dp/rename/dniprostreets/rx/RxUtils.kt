package ua.dp.rename.dniprostreets.rx

import io.reactivex.Single
import retrofit2.Response

fun <T> Single<Response<T>>.mapSafeResponse(): Single<T> {
   return map { response ->
      if (response.isSuccessful) {
         response.body()!!
      } else {
         throw IllegalStateException("Something wrong with API call response: ${response.errorBody()?.string()}")
      }
   }
}
