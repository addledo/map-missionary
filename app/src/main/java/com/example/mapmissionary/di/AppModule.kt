package com.example.mapmissionary.di

import com.example.mapmissionary.GridRefService
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


}