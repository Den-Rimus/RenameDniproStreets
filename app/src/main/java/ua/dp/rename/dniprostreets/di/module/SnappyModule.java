package ua.dp.rename.dniprostreets.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ua.dp.rename.dniprostreets.repo.SnappyRepository;

@Module
public class SnappyModule {

    @Provides @Singleton
    SnappyRepository provideSnappyRepository(Context context) {
        return new SnappyRepository(context);
    }
}
