package by.devnmisko.testbs.di

import android.app.Application
import by.devnmisko.data.di.LocalDatabaseModule
import by.devnmisko.data.di.RemoteRepositoryModule
import by.devnmisko.data.di.RetrofitModule
import by.devnmisko.data.di.SharedPrefModule
import by.devnmisko.data.di.SharedPrefRepositoryModule
import by.devnmisko.domain.di.UseCaseModule
import by.devnmisko.testbs.App
import by.devnmisko.testbs.MainActivity
import by.devnmisko.testbs.ui.camera.CameraFragment
import by.devnmisko.testbs.ui.camera.CameraViewModel
import by.devnmisko.testbs.ui.loginscreen.SignInFragment
import by.devnmisko.testbs.ui.loginscreen.SignUpFragment
import by.devnmisko.testbs.ui.loginscreen.StartUpFragmentViewModel
import by.devnmisko.testbs.ui.loginscreen.StartupFragment
import by.devnmisko.testbs.ui.mainscreen.MenuFragment
import by.devnmisko.testbs.ui.mainscreen.MenuFragmentViewModel
import by.devnmisko.testbs.ui.map.MapFragment
import by.devnmisko.testbs.ui.map.MapViewModel
import by.devnmisko.testbs.ui.photos.PhotosFragment
import by.devnmisko.testbs.ui.photos.PhotosViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class, AndroidModule::class, RetrofitModule::class, RemoteRepositoryModule::class, UseCaseModule::class,
        ViewModelModule::class, SharedPrefModule::class, SharedPrefRepositoryModule::class, LocalDatabaseModule::class]
)
interface ApplicationComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        @Named("application")
        fun application(app: Application): Builder

        fun build(): ApplicationComponent

    }

    fun inject(activity: MainActivity)
    fun inject(fragment: SignInFragment)
    fun inject(fragment: SignUpFragment)
    fun inject(fragment: StartupFragment)
    fun inject(model: StartUpFragmentViewModel)
    fun inject(fragment: MenuFragment)
    fun inject(model: MenuFragmentViewModel)
    fun inject(fragment: PhotosFragment)
    fun inject(model: PhotosViewModel)    
    
    fun inject(fragment: CameraFragment)
    fun inject(model: CameraViewModel)
    fun inject(fragment: MapFragment)
    fun inject(model: MapViewModel)

}