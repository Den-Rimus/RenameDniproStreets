package ua.dp.rename.dniprostreets.di.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.dp.rename.dniprostreets.api.LowercaseEnumTypeAdapterFactory;
import ua.dp.rename.dniprostreets.api.RenamedObjectsService;

import javax.inject.Singleton;

@Module
public class ApiModule {

   @Provides
   @Singleton
   RenamedObjectsService provideRenamedObjectsService(Retrofit retrofit) {
      return retrofit.create(RenamedObjectsService.class);
   }

   @Provides
   Retrofit provideRestAdapter(GsonConverterFactory gsonConverterFactory) {
      return new Retrofit.Builder()
            .baseUrl("http://rename.dp.ua/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(gsonConverterFactory)
            .build();
   }

   @Provides
   GsonConverterFactory provideGsonConverterFactory() {
      final Gson gson = new GsonBuilder()
            .serializeNulls()
            .registerTypeAdapterFactory(new LowercaseEnumTypeAdapterFactory("unknown"))
            .create();
      return GsonConverterFactory.create(gson);
   }
}
