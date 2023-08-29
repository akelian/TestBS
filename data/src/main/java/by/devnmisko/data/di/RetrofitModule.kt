package by.devnmisko.data.di

import by.devnmisko.data.AppContracts
import by.devnmisko.data.source.remote.CurlLoggingInterceptor
import by.devnmisko.data.source.remote.api.ImagesApi
import by.devnmisko.data.source.remote.api.SignInApi
import by.devnmisko.data.source.remote.api.SignUpApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
object RetrofitModule {

    @Singleton
    @Provides
    @Named("interceptor")
    fun provideLoggingInterceptor(): CurlLoggingInterceptor {
        return CurlLoggingInterceptor()
    }

    @Singleton
    @Provides
    @Named("Okhttp")
    fun provideOkHTTPClient(@Named("interceptor") interceptor: CurlLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    @Named("Retrofit")
    fun provideRetrofit(@Named("Okhttp") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppContracts.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideSignInApi(@Named("Retrofit") retrofit: Retrofit): SignInApi {
        return retrofit.create(SignInApi::class.java)
    }

    @Provides
    fun provideSignUpApi(@Named("Retrofit") retrofit: Retrofit): SignUpApi {
        return retrofit.create(SignUpApi::class.java)
    }

    @Provides
    fun provideImagesApi(@Named("Retrofit") retrofit: Retrofit): ImagesApi {
        return retrofit.create(ImagesApi::class.java)
    }


}