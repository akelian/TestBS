package by.devnmisko.testbs

import android.app.Application
import by.devnmisko.testbs.di.ApplicationComponent
import by.devnmisko.testbs.di.DaggerApplicationComponent
import timber.log.Timber

class App : Application() {

    private lateinit var applicationComponent : ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        val app = applicationContext as App
        app.getComponent()
        Timber.plant(Timber.DebugTree())
    }


    fun getComponent(): ApplicationComponent {
        applicationComponent = DaggerApplicationComponent.builder().application(this).build()

        return applicationComponent
    }
}