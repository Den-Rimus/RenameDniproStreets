package ua.dp.rename.dniprostreets

import android.app.Application
import ua.dp.rename.dniprostreets.core.ComponentInitializer
import ua.dp.rename.dniprostreets.di.component.AppComponent
import ua.dp.rename.dniprostreets.di.component.DaggerAppComponent
import ua.dp.rename.dniprostreets.di.module.AppModule
import javax.inject.Inject

open class App : Application() {

    private lateinit var component: AppComponent

    @Inject lateinit var initializers: MutableSet<ComponentInitializer>

    override fun onCreate() {
        super.onCreate()

        component = createComponent()

        component.inject(this)

        initializers.forEach { initializer -> initializer.init() }
    }

    val appComponent: AppComponent
        get() = component

    protected open fun createComponent(): AppComponent {
        return DaggerAppComponent.builder()
              .appModule(AppModule(this))
              .build()
    }
}
