package com.example.mapmissionary.di

import android.content.Context
import com.example.mapmissionary.GridRefService
import com.example.mapmissionary.Location
import com.example.mapmissionary.LocationHandler
import com.example.mapmissionary.LocationSearchViewModel
import com.example.mapmissionary.SharedViewModel
import com.example.mapmissionary.NetworkRepository
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
    fun provideGridRefService(networkRepository: NetworkRepository): GridRefService {
        return GridRefService(networkRepository)
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
    fun provideSharedViewModel(gridRefService: GridRefService): SharedViewModel {
        return SharedViewModel(gridRefService)
    }

    @Provides
    @Singleton
    fun provideLocationSearchViewModel(locationHandler: LocationHandler, gridRefService: GridRefService): LocationSearchViewModel {
        return LocationSearchViewModel(locationHandler, gridRefService)
    }

    @Provides
    @Singleton
    fun provideLocationHandler(@ApplicationContext context: Context): LocationHandler {
        return LocationHandler(context)
    }
}