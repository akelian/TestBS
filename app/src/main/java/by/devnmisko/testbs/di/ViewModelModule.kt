package by.devnmisko.testbs.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.devnmisko.testbs.ui.camera.CameraViewModel
import by.devnmisko.testbs.ui.loginscreen.StartUpFragmentViewModel
import by.devnmisko.testbs.ui.mainscreen.MenuFragmentViewModel
import by.devnmisko.testbs.ui.map.MapViewModel
import by.devnmisko.testbs.ui.photodetail.ImageDetailViewModel
import by.devnmisko.testbs.ui.photos.PhotosViewModel
import by.devnmisko.testbs.utils.ViewModelFactory
import by.devnmisko.testbs.utils.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.assisted.AssistedFactory
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(StartUpFragmentViewModel::class)
    @Singleton
    internal abstract fun startUpViewModel(viewModel: StartUpFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MenuFragmentViewModel::class)
    @Singleton
    internal abstract fun mainViewModel(viewModel: MenuFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PhotosViewModel::class)
    internal abstract fun photoViewModel(viewModel: PhotosViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CameraViewModel::class)
    internal abstract fun cameraViewModel(viewModel: CameraViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    internal abstract fun mapViewModel(viewModel: MapViewModel): ViewModel
//    @Binds
//    @IntoMap
//    @ViewModelKey(ImageDetailViewModel.Factory::class)
//    internal abstract fun imageDetailViewModel(viewModel: ImageDetailViewModel.Factory): ViewModel

}