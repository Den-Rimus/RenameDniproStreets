package ua.dp.rename.dniprostreets.di.module;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import ua.dp.rename.dniprostreets.api.LowercaseEnumTypeAdapterFactory;
import ua.dp.rename.dniprostreets.api.RenamedObjectsService;
import ua.dp.rename.dniprostreets.di.annotation.PerApplication;

@Module
public class ApiModule {

    @Provides @Singleton @PerApplication
    RenamedObjectsService provideRenamedObjectsService(RestAdapter restAdapter) {
        return restAdapter.create(RenamedObjectsService.class);
    }

    @Provides
    RestAdapter provideRestAdapter(GsonConverter gsonConverter) {
        return new RestAdapter.Builder()
                .setEndpoint("http://rename.dp.ua")
                .setLogLevel(RestAdapter.LogLevel.NONE)
                .setConverter(gsonConverter)
                .build();
    }

    @Provides
    GsonConverter provideGsonConverter() {
        return new GsonConverter(new GsonBuilder()
                .serializeNulls()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapterFactory(new LowercaseEnumTypeAdapterFactory("unknown"))
                .create());
    }
}
