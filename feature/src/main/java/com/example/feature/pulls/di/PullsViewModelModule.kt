package com.example.feature.pulls.di

import com.example.feature.pulls.domain.interfaces.PullsRepository
import com.example.feature.pulls.iu.viewmodel.PullsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object PullsViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideViewModel(repository: PullsRepository): PullsViewModel {
        return PullsViewModel(repository)
    }
}