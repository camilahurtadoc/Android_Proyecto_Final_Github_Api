package com.example.feature.pulls.iu

import com.example.feature.pulls.domain.model.PullModel

sealed class PullsState {
    data class Success(val pulls: List<PullModel>) : PullsState()
    data object Empty : PullsState()
    data object Error : PullsState()
    data object Loading : PullsState()
}