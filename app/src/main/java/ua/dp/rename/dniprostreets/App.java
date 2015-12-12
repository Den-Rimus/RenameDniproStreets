package ua.dp.rename.dniprostreets;

import android.app.Application;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import ua.dp.rename.dniprostreets.api.LowercaseEnumTypeAdapterFactory;
import ua.dp.rename.dniprostreets.api.RenamedObjectsService;

public class App extends Application {

    private static final Object BLOCK = new Object();

    private static RenamedObjectsService apiService;
    private static App instance;

    public static App getAppInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Api service
    ///////////////////////////////////////////////////////////////////////////

    public static RenamedObjectsService getApiService() {
        synchronized (BLOCK) {
            if (apiService == null) {
                buildApiService();
            }
        }

        return apiService;
    }

    private static void buildApiService() {
        RestAdapter retrofit = new RestAdapter.Builder()
                .setEndpoint("http://rename.dp.ua")
                .setLogLevel(RestAdapter.LogLevel.NONE)
                .setConverter(buildGsonConverter())
                .build();

        apiService = retrofit.create(RenamedObjectsService.class);
    }

    private static GsonConverter buildGsonConverter() {
        return new GsonConverter(new GsonBuilder()
                .serializeNulls()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapterFactory(new LowercaseEnumTypeAdapterFactory("unknown"))
                .create());
    }
}
