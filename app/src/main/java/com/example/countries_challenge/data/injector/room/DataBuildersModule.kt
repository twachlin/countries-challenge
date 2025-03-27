package com.example.countries_challenge.data.injector.room

import android.app.Application
import androidx.room.Room
import com.example.countries_challenge.data.feature.countries.local.CountryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBuildersModule {
    private companion object {
        const val COUNTRIES_DATA_BASE_NAME = "countries-db"
    }

    @Singleton
    @Provides
    fun providesRoomDataBase(application: Application): AppDataBase {
        return Room
            .databaseBuilder(application, AppDataBase::class.java, COUNTRIES_DATA_BASE_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun providesCountryDao(dataBase: AppDataBase): CountryDao {
        return dataBase.getCountryDao()
    }
}