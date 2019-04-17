package ua.dp.rename.dniprostreets

import android.app.Application
import ua.dp.rename.dniprostreets.core.ComponentInitializer
import ua.dp.rename.dniprostreets.di.component.AppComponent
import ua.dp.rename.dniprostreets.di.component.DaggerAppComponent
import ua.dp.rename.dniprostreets.di.module.ApiModule
import ua.dp.rename.dniprostreets.di.module.AppModule
import ua.dp.rename.dniprostreets.di.module.ComponentsInitializerModule
import ua.dp.rename.dniprostreets.di.module.RenamedObjectsRepoModule
import javax.inject.Inject

class App : Application() {

    private lateinit var component: AppComponent

    @Inject lateinit var initializers: MutableSet<ComponentInitializer>

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .apiModule(ApiModule())
                .renamedObjectsRepoModule(RenamedObjectsRepoModule())
                .componentsInitializerModule(ComponentsInitializerModule())
                .build()

        component.inject(this)

        initializers.forEach{ it.init() }
    }

    val appComponent: AppComponent
        get() = component
}
