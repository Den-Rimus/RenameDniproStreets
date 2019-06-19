package ua.dp.rename.dniprostreets.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import ua.dp.rename.dniprostreets.di.AppContext
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {

   @Singleton
   @Provides
   @AppContext
   fun provideAppContext(): Context = app
}
