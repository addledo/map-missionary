package com.example.mapmissionary.di

import com.example.mapmissionary.GridRefService
import com.example.mapmissionary.Location
import com.example.mapmissionary.LocationSearchViewModel
import com.example.mapmissionary.SharedViewModel
import com.example.mapmissionary.NetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideLocationSearchViewModel(): LocationSearchViewModel {
        return LocationSearchViewModel()
    }

}