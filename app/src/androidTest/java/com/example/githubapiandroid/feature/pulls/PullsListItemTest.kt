package com.example.githubapiandroid.feature.pulls

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.feature.pulls.domain.model.PullModel
import com.example.feature.pulls.domain.model.PullUserModel
import com.example.feature.pulls.iu.compose.PullsListItem
import com.example.githubapiandroid.MainActivity
import org.junit.Rule
import org.junit.Test

class PullsListItemTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testPullsListItem() {

        composeTestRule.activity.setContent {
            PullsListItem(
                pullModel = PullModel(
                    title = "Pull request 1",
                    body = "Descripción de pull request",
                    createdAt = "2023-06-01T12:00:00Z",
                    htmlUrl = "",
                    user = PullUserModel(
                        login = "camilahurtadoc",
                        avatarUrl = "https://avatars.githubusercontent.com/u/193549342?v=4"
                    )
                ),
            )
        }

        composeTestRule.onNodeWithText("Pull request 1").assertIsDisplayed()
    }
}