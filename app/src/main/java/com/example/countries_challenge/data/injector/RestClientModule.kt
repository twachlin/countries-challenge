package com.example.countries_challenge.data.injector

import android.content.Context
import com.example.countries_challenge.data.feature.countries.net.CountriesApi
import com.example.countries_challenge.data.util.net.interceptor.ConnectivityInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RestClientModule {
    private companion object {
        private const val BASE_URL = "https://gist.githubusercontent.com/hernan-uala/"
        private const val TIMEOUT_THRESHOLD = 50L
    }

    @Provides
    @Named("baseUrl")
    fun provideBaseUrl() = BASE_URL

    @Provides
    @Named("Base Client")
    fun providesBaseHttpClient(
        @Named("AppContext") context: Context,
    ): OkHttpClient.Builder {
        val okHttpBuilder = OkHttpClient.Builder().apply {
            connectTimeout(TIMEOUT_THRESHOLD, TimeUnit.SECONDS)
            readTimeout(TIMEOUT_THRESHOLD, TimeUnit.SECONDS)
            writeTimeout(TIMEOUT_THRESHOLD, TimeUnit.SECONDS)
            addInterceptor(ConnectivityInterceptor(context))
        }
        return okHttpBuilder
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    @Named("Retrofit")
    fun provideRetrofit(
        gsonConverter: GsonConverterFactory,
        @Named("Base Client") client: OkHttpClient.Builder,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverter)
            .client(client.build())
            .build()
    }

    @Provides
    @Singleton
    fun provideCountriesApi(@Named("Retrofit") retrofit: Retrofit): CountriesApi {
        return retrofit.create(CountriesApi::class.java)
    }
}