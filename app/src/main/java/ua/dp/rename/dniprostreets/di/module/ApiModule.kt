package ua.dp.rename.dniprostreets.di.module

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ua.dp.rename.dniprostreets.api.LowercaseEnumTypeAdapterFactory
import ua.dp.rename.dniprostreets.api.RenamedObjectsService
import javax.inject.Singleton

@Module
class ApiModule {

   @Provides
   @Singleton
   fun provideRenamedObjectsService(retrofit: Retrofit): RenamedObjectsService {
      return retrofit.create(RenamedObjectsService::class.java)
   }

   @Provides
   @Singleton
   fun provideRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit {
      return Retrofit.Builder()
            .baseUrl("http://rename.dp.ua/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(gsonConverterFactory)
            .build()
   }

   @Provides
   @Singleton
   fun provideGsonConverterFactory(): GsonConverterFactory {
      val gson = GsonBuilder()
            .serializeNulls()
            .registerTypeAdapterFactory(LowercaseEnumTypeAdapterFactory("unknown"))
            .create()

      return GsonConverterFactory.create(gson)
   }
}
