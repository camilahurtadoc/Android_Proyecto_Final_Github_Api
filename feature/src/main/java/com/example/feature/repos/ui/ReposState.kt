package com.example.feature.repos.ui

import com.example.feature.repos.domain.model.ReposItemsModel

sealed class ReposState {
    data class Success(val repos: ReposItemsModel) : ReposState()
    data class LoadMore(val repos: ReposItemsModel) : ReposState()
    data object Empty : ReposState()
    data object Error : ReposState()
    data object Loading : ReposState()
}
