package com.example.feature.pulls.iu.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.feature.R
import com.example.feature.pulls.domain.model.PullModel
import com.example.feature.pulls.domain.model.PullUserModel
import com.example.feature.pulls.iu.PullsState
import com.example.feature.pulls.iu.viewmodel.PullsViewModel

@Composable
fun PullsScreen(
    navController: NavController,
    viewModel: PullsViewModel,
    owner: String,
    repo: String
) {

    val state by viewModel.pullsState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getPulls(owner, repo)
    }

    Scaffold(
        topBar = { Header(repo = repo, navController = navController) }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            when (state) {
                is PullsState.Success -> SetupSuccess(items = (state as PullsState.Success).pulls)
                is PullsState.Empty -> SetupEmpty()
                is PullsState.Error -> SetupError(tryAgain = { viewModel.getPulls(owner, repo) } )
                is PullsState.Loading -> SetupLoading()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
    repo: String,
    navController: NavController
) {
    TopAppBar(
        title = {
            Text(text = repo)
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(R.drawable.arrow_back_24px),
                    contentDescription = stringResource(R.string.back_to_repos)
                )
            }
        }
    )
}

@Composable
fun SetupSuccess(items: List<PullModel>) {
    LazyColumn {
        items(items) { item ->
            PullsListItem(
                pullModel = item
            )
        }
    }
}

@Composable
fun SetupEmpty() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(R.string.no_pulls_found)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SetupSuccessPreview() {
    SetupSuccess(
        items = listOf(
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
    )
}

@Composable
fun SetupError(tryAgain: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        IconButton(
            modifier = Modifier.align(Alignment.TopCenter),
            onClick = { tryAgain.invoke() }
        ) {
            Icon(
                modifier = Modifier.size(48.dp),
                painter = painterResource(R.drawable.refresh_24px),
                contentDescription = stringResource(R.string.menu),
            )
        }
        Text(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = stringResource(R.string.error_loading_pulls)
        )
    }
}

@Composable
fun SetupLoading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
        Text(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = stringResource(R.string.loading_pulls)
        )
    }
}