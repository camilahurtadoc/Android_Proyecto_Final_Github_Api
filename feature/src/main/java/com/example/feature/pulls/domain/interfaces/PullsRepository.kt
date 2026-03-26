package com.example.feature.pulls.domain.interfaces

import com.example.feature.pulls.domain.model.PullModel

interface PullsRepository {
    suspend fun getPulls(owner: String, repo: String): List<PullModel>
}