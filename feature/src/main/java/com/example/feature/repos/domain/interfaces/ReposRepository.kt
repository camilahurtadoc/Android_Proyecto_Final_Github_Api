package com.example.feature.repos.domain.interfaces

import com.example.feature.repos.domain.model.ReposItemsModel

interface ReposRepository {
    suspend fun getRepos(currentPage: Int = 0): ReposItemsModel
}