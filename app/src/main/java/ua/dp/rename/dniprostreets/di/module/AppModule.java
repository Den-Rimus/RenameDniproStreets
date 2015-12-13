package ua.dp.rename.dniprostreets.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ua.dp.rename.dniprostreets.App;

@Module
public class AppModule {

    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides @Singleton
    Context provideApplicationContext() {
        return application;
    }
}
