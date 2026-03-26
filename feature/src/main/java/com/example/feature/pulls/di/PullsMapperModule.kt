package com.example.feature.pulls.di

import com.example.feature.pulls.data.mapper.PullsMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PullsMapperModule {

    @Provides
    fun provideMapper(): PullsMapper {
        return PullsMapper()
    }
}