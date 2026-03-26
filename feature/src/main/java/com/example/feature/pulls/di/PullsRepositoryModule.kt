package com.example.feature.pulls.di

import com.example.feature.pulls.data.mapper.PullsMapper
import com.example.feature.pulls.data.repository.PullsRepositoryImpl
import com.example.feature.pulls.data.service.PullsService
import com.example.feature.pulls.domain.interfaces.PullsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PullsRepositoryModule {

    @Provides
    fun provideRepository(service: PullsService, mapper: PullsMapper): PullsRepository {
        return PullsRepositoryImpl(service, mapper)
    }
}