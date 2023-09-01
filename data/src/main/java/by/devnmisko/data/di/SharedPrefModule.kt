package by.devnmisko.data.di

import android.content.Context
import by.devnmisko.data.source.local.datasource.SharedPreferencesSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object SharedPrefModule {

    @Provides
    @Singleton
     fun providesSharedPref(context: Context) : SharedPreferencesSource {
        return SharedPreferencesSource(context = context)
    }
}