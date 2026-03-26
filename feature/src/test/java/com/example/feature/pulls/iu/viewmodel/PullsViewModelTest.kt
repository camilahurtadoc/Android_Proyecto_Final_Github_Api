package com.example.feature.pulls.iu.viewmodel

import com.example.feature.pulls.domain.interfaces.PullsRepository
import com.example.feature.pulls.domain.model.PullModel
import com.example.feature.pulls.domain.model.PullUserModel
import com.example.feature.pulls.iu.PullsState
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
class UserViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val repository = mockk<PullsRepository>()
    private val viewModel = PullsViewModel(repository)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getPulls success`() = runTest {
        val pulls = getListOfPulls()
        coEvery { repository.getPulls(any(), any()) } returns pulls

        viewModel.getPulls("owner", "repo")

        advanceUntilIdle()

        val state = viewModel.pullsState.first()
        assertEquals(state, PullsState.Success(pulls))
    }

    @Test
    fun `getPulls error`() = runTest {
        val errorMessage = "Network error"
        coEvery { repository.getPulls(any(), any()) } throws Exception(errorMessage)

        viewModel.getPulls("owner", "repo")

        advanceUntilIdle()

        val state = viewModel.pullsState.first()
        assertEquals(state, PullsState.Error)
    }

    private fun getListOfPulls(): List<PullModel> = listOf(
        PullModel(
            title = "Pull request 1",
            body = "Descripción de pull request",
            createdAt = "2023-06-01T12:00:00Z",
            htmlUrl = "",
            user = PullUserModel(
                login = "camilahurtadoc",
                avatarUrl = "https://avatars.githubusercontent.com/u/193549342?v=4"
            )
        )
    )
}