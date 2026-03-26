package com.example.feature.repos.di

import com.example.feature.repos.data.mapper.ReposMapper
import com.example.feature.repos.data.repository.ReposRepositoryImpl
import com.example.feature.repos.data.service.ReposService
import com.example.feature.repos.domain.interfaces.ReposRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ReposRepositoryModule {

    @Provides
    fun provideRepository(service: ReposService, mapper: ReposMapper): ReposRepository {
        return ReposRepositoryImpl(service, mapper)
    }
}