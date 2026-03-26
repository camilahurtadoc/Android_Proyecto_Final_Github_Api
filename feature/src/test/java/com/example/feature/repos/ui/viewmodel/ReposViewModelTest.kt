package com.example.feature.repos.ui.viewmodel

import com.example.feature.repos.domain.interfaces.ReposRepository
import com.example.feature.repos.domain.model.ReposItemsModel
import com.example.feature.repos.ui.ReposState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ReposViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val repository = mockk<ReposRepository>()
    private val viewModel = ReposViewModel(repository)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getRepos success`() = runTest {
        val repos = getListOfRepos()
        coEvery { repository.getRepos(any()) } returns repos

        viewModel.getRepos()

        advanceUntilIdle()

        val state = viewModel.reposState.first()
        assertEquals(state, ReposState.Success(repos))
    }

    @Test
    fun `getRepos error`() = runTest {
        val errorMessage = "Network error"
        coEvery { repository.getRepos(any()) } throws Exception(errorMessage)

        viewModel.getRepos()

        advanceUntilIdle()

        val state = viewModel.reposState.first()
        assertEquals(state, ReposState.Error)
    }

    private fun getListOfRepos(): ReposItemsModel = ReposItemsModel(
        items = listOf()
    )
}