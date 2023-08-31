package by.devnmisko.data.di

import android.content.Context
import by.devnmisko.data.source.local.ImagesDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDatabaseModule {

    @Singleton
    @Provides
    fun provideImagesDatabase(appContext: Context) = ImagesDataBase.create(appContext)

    @Provides
    fun provideImagesEntryDao(imagesDataBase: ImagesDataBase) = imagesDataBase.imagesDao()

    @Provides
    fun provideRemoteKeyDao(imagesDataBase: ImagesDataBase) = imagesDataBase.remoteKeyDao()


}
