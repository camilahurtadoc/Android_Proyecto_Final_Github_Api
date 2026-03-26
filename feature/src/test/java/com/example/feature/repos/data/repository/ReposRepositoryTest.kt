package com.example.feature.repos.data.repository

import com.example.feature.repos.data.dto.ReposItemsDTO
import com.example.feature.repos.data.mapper.ReposMapper
import com.example.feature.repos.data.service.ReposService
import com.example.feature.repos.domain.model.ReposItemsModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ReposRepositoryTest {

    private val service: ReposService = mockk()
    private val mapper: ReposMapper = mockk()
    private val pullsRepository = ReposRepositoryImpl(service, mapper)

    @Test
    fun `pulls repository test`() = runTest {
        coEvery { service.getRepos() } returns ReposItemsDTO(items = listOf())
        every { mapper.toReposItemsModel(any()) } returns ReposItemsModel(items = listOf())

        val expected = ReposItemsModel(items = listOf())
        val result = pullsRepository.getRepos()

        assert(result == expected)
    }
}