package com.example.githubapiandroid.feature.pulls

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.NavHostController
import com.example.feature.pulls.domain.model.PullModel
import com.example.feature.pulls.domain.model.PullUserModel
import com.example.feature.pulls.iu.PullsState
import com.example.feature.pulls.iu.compose.PullsScreen
import com.example.feature.pulls.iu.viewmodel.PullsViewModel
import com.example.githubapiandroid.MainActivity
import io.mockk.Awaits
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.StateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PullsScreenTest {

    private val viewModel = mockk<PullsViewModel>()
    private val stateFlow = mockk<StateFlow<PullsState>>()
    private val navController = mockk<NavHostController>()

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        coEvery { viewModel.getPulls(any(), any()) } just Runs
        coEvery { stateFlow.collect(any()) } just Awaits
        every { viewModel.pullsState } returns stateFlow
    }

    @Test
    fun testPullsScreen() {
        every { stateFlow.value } returns PullsState.Success(emptyList())

        composeTestRule.activity.setContent {
            PullsScreen(
                viewModel = viewModel,
                navController = navController,
                owner = "owner",
                repo = "repo"
            )
        }

        composeTestRule.onNodeWithText("repo").assertIsDisplayed()
    }

    @Test
    fun testPullsScreenSuccess() {
        every { stateFlow.value } returns PullsState.Success(getPulls())

        composeTestRule.activity.setContent {
            PullsScreen(
                viewModel = viewModel,
                navController = navController,
                owner = "owner",
                repo = "repo"
            )
        }

        composeTestRule.onNodeWithText("Pull request 1").assertIsDisplayed()
    }

    @Test
    fun testPullsScreenError() {
        every { stateFlow.value } returns PullsState.Error

        composeTestRule.activity.setContent {
            PullsScreen(
                viewModel = viewModel,
                navController = navController,
                owner = "owner",
                repo = "repo"
            )
        }

        composeTestRule.onNodeWithText("Error al cargar lista").assertIsDisplayed()
    }

    @Test
    fun testPullsScreenLoading() {
        every { stateFlow.value } returns PullsState.Loading

        composeTestRule.activity.setContent {
            PullsScreen(
                viewModel = viewModel,
                navController = navController,
                owner = "owner",
                repo = "repo"
            )
        }

        composeTestRule.onNodeWithText("Cargando lista de pulls...").assertIsDisplayed()
    }

    @Test
    fun testPullsScreenEmpty() {
        every { stateFlow.value } returns PullsState.Empty

        composeTestRule.activity.setContent {
            PullsScreen(
                viewModel = viewModel,
                navController = navController,
                owner = "owner",
                repo = "repo"
            )
        }

        composeTestRule.onNodeWithText("Ninguna pull request encontrada").assertIsDisplayed()
    }

    private fun getPulls(): List<PullModel> =
        listOf(
            PullModel(
                title = "Pull request 1",
                body = "Descripción de pull request",
                createdAt = "2023-06-01T12:00:00Z",
                htmlUrl = "",
                user = PullUserModel(
                    login = "camilahurtadoc",
                    avatarUrl = "https://avatars.githubusercontent.com/u/193549342?v=4"
                )
            ),
            PullModel(
                title = "Pull request 1",
                body = "Descripción de pull request",
                createdAt = "2023-06-01T12:00:00Z",
                htmlUrl = "",
                user = PullUserModel(
                    login = "camilahurtadoc",
                    avatarUrl = "https://avatars.githubusercontent.com/u/193549342?v=4"
                )
            ),
            PullModel(
                title = "Pull request 1",
                body = "Descripción de pull request",
                createdAt = "2023-06-01T12:00:00Z",
                htmlUrl = "",
                user = PullUserModel(
                    login = "camilahurtadoc",
                    avatarUrl = "https://avatars.githubusercontent.com/u/193549342?v=4"
                )
            ),
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