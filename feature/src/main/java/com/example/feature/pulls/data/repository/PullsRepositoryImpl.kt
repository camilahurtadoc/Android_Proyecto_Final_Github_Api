package com.example.feature.pulls.data.repository

import com.example.feature.pulls.data.mapper.PullsMapper
import com.example.feature.pulls.data.service.PullsService
import com.example.feature.pulls.domain.interfaces.PullsRepository
import com.example.feature.pulls.domain.model.PullModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PullsRepositoryImpl(
    private val service: PullsService,
    private val mapper: PullsMapper
): PullsRepository {
    override suspend fun getPulls(owner: String, repo: String): List<PullModel> =
        withContext(Dispatchers.IO) {
            val response = service.getPulls(owner, repo)
            mapper.toPullsModel(response)
        }
}