package com.example.feature.repos.data.repository

import com.example.feature.repos.data.mapper.ReposMapper
import com.example.feature.repos.data.service.ReposService
import com.example.feature.repos.domain.interfaces.ReposRepository
import com.example.feature.repos.domain.model.ReposItemsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReposRepositoryImpl(
    private val service: ReposService,
    private val mapper: ReposMapper
) : ReposRepository {

    override suspend fun getRepos(currentPage: Int): ReposItemsModel = withContext(Dispatchers.IO) {
        val response = service.getRepos(page = currentPage)
        mapper.toReposItemsModel(response)
    }
}
