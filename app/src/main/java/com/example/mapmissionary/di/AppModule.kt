package com.example.mapmissionary.di

import android.content.Context
import com.example.mapmissionary.utilities.GeoDojoService
import com.example.mapmissionary.data.Location
import com.example.mapmissionary.utilities.DeviceLocationHandler
import com.example.mapmissionary.view_models.LocationSearchViewModel
import com.example.mapmissionary.view_models.SharedViewModel
import com.example.mapmissionary.utilities.NetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGridRefService(networkRepository: NetworkRepository): GeoDojoService {
        return GeoDojoService(networkRepository)
    }

    @Provides
    @Singleton
    fun provideLocation(): Location {
        return Location()
    }

    @Provides
    @Singleton
    fun provideNetworkRepository(): NetworkRepository {
        return NetworkRepository()
    }

    @Provides
    @Singleton
    fun provideSharedViewModel(geoDojoService: GeoDojoService): SharedViewModel {
        return SharedViewModel(geoDojoService)
    }

    @Provides
    @Singleton
    fun provideLocationSearchViewModel(deviceLocationHandler: DeviceLocationHandler, geoDojoService: GeoDojoService): LocationSearchViewModel {
        return LocationSearchViewModel(deviceLocationHandler, geoDojoService)
    }

    @Provides
    @Singleton
    fun provideLocationHandler(@ApplicationContext context: Context): DeviceLocationHandler {
        return DeviceLocationHandler(context)
    }
}