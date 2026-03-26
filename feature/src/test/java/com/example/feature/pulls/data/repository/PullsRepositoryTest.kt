package com.example.feature.pulls.data.repository

import com.example.feature.pulls.data.dto.PullDTO
import com.example.feature.pulls.data.mapper.PullsMapper
import com.example.feature.pulls.data.service.PullsService
import com.example.feature.pulls.domain.model.PullModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class PullsRepositoryTest {

    private val service: PullsService = mockk()
    private val mapper: PullsMapper = mockk()
    private val pullsRepository = PullsRepositoryImpl(service, mapper)

    @Test
    fun `pulls repository test`() = runTest {
        coEvery { service.getPulls(any(), any()) } returns listOf<PullDTO>()
        every { mapper.toPullsModel(any()) } returns listOf<PullModel>()

        val expected = listOf<PullModel>()
        val result = pullsRepository.getPulls("test", "test")

        assert(result == expected)
    }
}