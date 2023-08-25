package by.devnmisko.data.di

import by.devnmisko.data.repository.SharedPrefRepositoryImpl
import by.devnmisko.domain.repository.SharedPrefRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class SharedPrefRepositoryModule {

    @Binds
    @Singleton
    internal abstract fun bindSharedPrefRepository(repository: SharedPrefRepositoryImpl) :  SharedPrefRepository
}