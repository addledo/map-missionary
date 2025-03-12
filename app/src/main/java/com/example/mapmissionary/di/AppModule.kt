package com.example.mapmissionary.di

import com.example.mapmissionary.GridRefService
import com.example.mapmissionary.Location
import com.example.mapmissionary.SharedViewModel
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
    fun provideGridRefService(): GridRefService {
        return GridRefService()
    }

    @Provides
    @Singleton
    fun provideLocation(): Location {
        return Location()
    }

    @Provides
    @Singleton
    fun provideSharedViewModel(): SharedViewModel {
        return SharedViewModel()
    }

}