package by.devnmisko.testbs.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AndroidModule {

    @Binds
    @Singleton
    abstract fun bindContext(app: Application) : Context


}