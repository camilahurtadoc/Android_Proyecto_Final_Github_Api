package com.example.feature.repos.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.feature.R
import com.example.feature.repos.domain.model.ReposModel
import com.example.feature.repos.domain.model.ReposOwnerModel
import com.example.feature.repos.ui.ReposState
import com.example.feature.repos.ui.viewmodel.ReposViewModel

@Composable
fun ReposScreen(
    viewModel: ReposViewModel,
    navController: NavHostController
) {

    val state by viewModel.reposState.collectAsState()
    val currentItems = remember { mutableListOf<ReposModel>() }

    LaunchedEffect(key1 = Unit) {
        viewModel.getRepos()
    }

    Scaffold(
        topBar = { Header() }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            when (state) {
                is ReposState.Success -> {
                    currentItems.addAll((state as ReposState.Success).repos.items)
                    SetupSuccess(
                        items = currentItems,
                        loadMore = viewModel::loadMore,
                        navController = navController
                    )
                }
                is ReposState.LoadMore -> {
                    currentItems.addAll((state as ReposState.LoadMore).repos.items)
                    SetupSuccess(
                        items = currentItems,
                        loadMore = viewModel::loadMore,
                        navController = navController
                    )
                }
                is ReposState.Empty -> SetupEmpty()
                is ReposState.Error -> SetupError(viewModel::getRepos)
                is ReposState.Loading -> SetupLoading()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Header() {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.repos_list))
        },
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(R.drawable.menu_24px),
                    contentDescription = stringResource(R.string.menu)
                )
            }
        }
    )
}

@Composable
fun SetupSuccess(
    loadMore: () -> Unit,
    items: List<ReposModel>,
    navController: NavHostController
) {
    val listState = rememberLazyListState()

    val reachedBottom: Boolean by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 &&
                    lastVisibleItem?.index == listState.layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(reachedBottom) {
        if (reachedBottom) loadMore()
    }

    LazyColumn(
        state = listState
    ) {
        items(items) { item ->
            ReposListItem(
                reposModel = item,
                navController = navController
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
            text = stringResource(R.string.no_repos_found)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SetupSuccessPreview() {
    val items = listOf(
        ReposModel(
            name = "GitHub API Android",
            description = "App Android para mostrar el contenido de la API de GitHub",
            stargazersCount = 100,
            forksCount = 50,
            owner = ReposOwnerModel(
                login = "camilahurtadoc",
                avatarUrl = "https://avatars.githubusercontent.com/u/193549342?v=4"
            )
        ),
        ReposModel(
            name = "GitHub API Android",
            description = "App Android para mostrar el contenido de la API de GitHub",
            stargazersCount = 100,
            forksCount = 50,
            owner = ReposOwnerModel(
                login = "camilahurtadoc",
                avatarUrl = "https://avatars.githubusercontent.com/u/193549342?v=4"
            )
        ),
        ReposModel(
            name = "GitHub API Android",
            description = "App Android para mostrar el contenido de la API de GitHub",
            stargazersCount = 100,
            forksCount = 50,
            owner = ReposOwnerModel(
                login = "camilahurtadoc",
                avatarUrl = "https://avatars.githubusercontent.com/u/193549342?v=4"
            )
        )
    )

    SetupSuccess(
        loadMore = { },
        items = items,
        navController = rememberNavController()
    )
}

@Composable
fun SetupError(tryAgain: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
    ) {
        IconButton(
            modifier = Modifier.align(Alignment.TopCenter),
            onClick = { tryAgain.invoke() }
        ) {
            Icon(
                modifier = Modifier
                    .size(48.dp),
                painter = painterResource(R.drawable.refresh_24px),
                contentDescription = stringResource(R.string.menu),

            )
        }
        Text(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = stringResource(R.string.error_loading_repos)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SetupErrorPreview() {
    SetupError(tryAgain = {})
}

@Preview(showBackground = true)
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
            text = "Cargando lista de repositorios..."
        )
    }
}