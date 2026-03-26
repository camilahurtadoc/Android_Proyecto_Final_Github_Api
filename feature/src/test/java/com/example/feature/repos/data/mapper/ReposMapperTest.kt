package com.example.feature.repos.data.mapper

import com.example.feature.repos.data.dto.ReposItemsDTO
import com.example.feature.repos.domain.model.ReposItemsModel
import org.junit.Test

class ReposMapperTest {

    private val mapper = ReposMapper()

    @Test
    fun `test repos mapper`() {
        val mappedRepos = mapper.toReposItemsModel(getReposItemsDTO())
        val expectedRepos = getReposItemsModel()
        assert(mappedRepos == expectedRepos)
    }

    private fun getReposItemsDTO(): ReposItemsDTO =
        ReposItemsDTO(
            items = listOf()
        )

    private fun getReposItemsModel(): ReposItemsModel =
        ReposItemsModel(
            items = listOf()
        )
}