package com.example.githubapiandroid.feature.repos

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import com.example.feature.repos.domain.model.ReposItemsModel
import com.example.feature.repos.domain.model.ReposModel
import com.example.feature.repos.domain.model.ReposOwnerModel
import com.example.feature.repos.ui.ReposState
import com.example.feature.repos.ui.compose.ReposScreen
import com.example.feature.repos.ui.viewmodel.ReposViewModel
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

class ReposScreenTest {

    private val viewModel = mockk<ReposViewModel>()
    private val stateFlow = mockk<StateFlow<ReposState>>()
    private val navController = mockk<NavHostController>()

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        coEvery { viewModel.getRepos() } just Runs
        coEvery { viewModel.loadMore() } just Runs
        coEvery { stateFlow.collect(any()) } just Awaits
        every { viewModel.reposState } returns stateFlow
    }

    @Test
    fun testReposScreen() {
        every { stateFlow.value } returns ReposState.Success(getReposItemsModel())

        composeTestRule.activity.setContent {
            ReposScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composeTestRule.onNodeWithText("Lista de Repositorios").assertIsDisplayed()
    }

    @Test
    fun testReposScreenSuccess() {
        every { stateFlow.value } returns ReposState.Success(getReposItemsModel())

        composeTestRule.activity.setContent {
            ReposScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composeTestRule.onAllNodesWithText("name1")[0].assertIsDisplayed()
    }

    @Test
    fun testReposScreenError() {
        every { stateFlow.value } returns ReposState.Error

        composeTestRule.activity.setContent {
            ReposScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composeTestRule.onNodeWithText("Error al cargar lista").assertIsDisplayed()
    }

    @Test
    fun testReposScreenLoading() {
        every { stateFlow.value } returns ReposState.Loading

        composeTestRule.activity.setContent {
            ReposScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composeTestRule.onNodeWithText("Cargando lista de repositorios...").assertIsDisplayed()
    }

    @Test
    fun testReposScreenEmpty() {
        every { stateFlow.value } returns ReposState.Empty

        composeTestRule.activity.setContent {
            ReposScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composeTestRule.onNodeWithText("Ningún repositório encontrado").assertIsDisplayed()
    }

    @Test
    fun testReposScreenGoToPulls() {
        every { stateFlow.value } returns ReposState.Success(getReposItemsModel())
        every {
            navController.navigate("pulls/login1/name1", null, null)
        } just Runs

        composeTestRule.activity.setContent {
            ReposScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composeTestRule.onAllNodesWithText("name1")[0].performClick()

        composeTestRule.onNodeWithContentDescription("Volver a la lista de repositorios")
            .isDisplayed()
    }

    private fun getReposItemsModel(): ReposItemsModel = ReposItemsModel(
        items = listOf(
            ReposModel(
                name = "name1",
                description = "description1",
                stargazersCount = 1,
                forksCount = 1,
                owner = ReposOwnerModel(
                    login = "login1",
                    avatarUrl = "avatarUrl1"
                )
            ),
            ReposModel(
                name = "name2",
                description = "description2",
                stargazersCount = 2,
                forksCount = 2,
                owner = ReposOwnerModel(
                    login = "login2",
                    avatarUrl = "avatarUrl2"
                )
            )
        )
    )
}