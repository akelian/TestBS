package by.devnmisko.data.di

import by.devnmisko.data.repository.AccountRepositoryImpl
import by.devnmisko.domain.repository.AccountRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RemoteRepositoryModule {

    @Binds
    @Singleton
    internal abstract fun bindAccountRepository(repository: AccountRepositoryImpl): AccountRepository

}