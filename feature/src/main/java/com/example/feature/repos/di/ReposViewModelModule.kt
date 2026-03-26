package com.example.feature.repos.di

import com.example.feature.repos.domain.interfaces.ReposRepository
import com.example.feature.repos.ui.viewmodel.ReposViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ReposViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideViewModel(repository: ReposRepository): ReposViewModel {
        return ReposViewModel(repository)
    }
}