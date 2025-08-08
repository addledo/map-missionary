package com.jontypine.mapmissionary.di

import android.content.Context
import com.jontypine.mapmissionary.data.Location
import com.jontypine.mapmissionary.interfaces.ExtrasProvider
import com.jontypine.mapmissionary.interfaces.GridRefProvider
import com.jontypine.mapmissionary.interfaces.LatLongProvider
import com.jontypine.mapmissionary.interfaces.LocationSearchProvider
import com.jontypine.mapmissionary.utilities.DeviceLocationHandler
import com.jontypine.mapmissionary.utilities.GeoDojoService
import com.jontypine.mapmissionary.utilities.NetworkRepository
import com.jontypine.mapmissionary.utilities.OsgbConverter
import com.jontypine.mapmissionary.view_models.SharedViewModel
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
    fun provideGridRefProvider(): GridRefProvider {
        return OsgbConverter()
    }

    @Provides
    @Singleton
    fun provideLatLongProvider(): LatLongProvider {
        return OsgbConverter()
    }

    @Provides
    @Singleton
    fun provideLocationSearchProvider(geoDojoService: GeoDojoService): LocationSearchProvider {
        return geoDojoService
    }

    @Provides
    @Singleton
    fun provideExtrasProvider(geoDojoService: GeoDojoService): ExtrasProvider {
        return geoDojoService
    }
}