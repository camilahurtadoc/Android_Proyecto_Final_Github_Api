package com.example.githubapiandroid.feature.repos

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.NavHostController
import com.example.feature.repos.domain.model.ReposModel
import com.example.feature.repos.domain.model.ReposOwnerModel
import com.example.feature.repos.ui.compose.ReposListItem
import com.example.githubapiandroid.MainActivity
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class ReposListItemTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val navController = mockk<NavHostController>()

    @Test
    fun testReposListItem() {

        composeTestRule.activity.setContent {
            ReposListItem(
                reposModel = ReposModel(
                    name = "name1",
                    description = "description1",
                    stargazersCount = 1,
                    forksCount = 1,
                    owner = ReposOwnerModel(
                        login = "login1",
                        avatarUrl = "avatarUrl1"
                    )
                ),
                navController = navController
            )
        }

        composeTestRule.onNodeWithText("name1").assertIsDisplayed()
    }
}