package com.example.mapmissionary.di

import android.content.Context
import com.example.mapmissionary.data.Location
import com.example.mapmissionary.interfaces.GridRefProvider
import com.example.mapmissionary.interfaces.LocationSearchProvider
import com.example.mapmissionary.utilities.DeviceLocationHandler
import com.example.mapmissionary.utilities.GeoDojoService
import com.example.mapmissionary.utilities.NetworkRepository
import com.example.mapmissionary.view_models.LocationDetailsViewModel
import com.example.mapmissionary.view_models.LocationSearchViewModel
import com.example.mapmissionary.view_models.SharedViewModel
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
    fun provideSharedViewModel(): SharedViewModel {
        return SharedViewModel()
    }

    @Provides
    @Singleton
    fun provideLocationSearchViewModel(
        deviceLocationHandler: DeviceLocationHandler,
        gridRefProvider: GridRefProvider,
        locationSearchProvider: LocationSearchProvider
    ): LocationSearchViewModel {
        return LocationSearchViewModel(
            deviceLocationHandler,
            gridRefProvider,
            locationSearchProvider
        )
    }

    @Provides
    @Singleton
    fun provideLocationDetailsViewModel(geoDojoService: GeoDojoService): LocationDetailsViewModel {
        return LocationDetailsViewModel(geoDojoService)
    }

    @Provides
    @Singleton
    fun provideLocationHandler(@ApplicationContext context: Context): DeviceLocationHandler {
        return DeviceLocationHandler(context)
    }

    @Provides
    @Singleton
    fun provideGeoDojoService(networkRepository: NetworkRepository): GeoDojoService {
        return GeoDojoService(networkRepository)
    }

    @Provides
    @Singleton
    fun provideGridRefProvider(geoDojoService: GeoDojoService): GridRefProvider {
        return geoDojoService
    }

    @Provides
    @Singleton
    fun provideLocationSearchProvider(geoDojoService: GeoDojoService): LocationSearchProvider {
        return geoDojoService
    }
}