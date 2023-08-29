package by.devnmisko.data.di

import by.devnmisko.data.repository.AccountRepositoryImpl
import by.devnmisko.data.repository.ImagesRepositoryImpl
import by.devnmisko.domain.repository.AccountRepository
import by.devnmisko.domain.repository.ImagesRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RemoteRepositoryModule {

    @Binds
    @Singleton
    internal abstract fun bindAccountRepository(repository: AccountRepositoryImpl): AccountRepository

    @Binds
    @Singleton
    internal abstract fun bindImagesRepository(repository: ImagesRepositoryImpl): ImagesRepository

}