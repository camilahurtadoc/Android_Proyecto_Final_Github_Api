package com.example.feature.repos.di

import com.example.feature.repos.data.mapper.ReposMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ReposMapperModule {

    @Provides
    fun provideMapper(): ReposMapper {
        return ReposMapper()
    }
}