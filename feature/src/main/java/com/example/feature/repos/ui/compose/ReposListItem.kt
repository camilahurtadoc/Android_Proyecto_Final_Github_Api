package com.example.feature.repos.ui.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.feature.R
import com.example.feature.commons.utils.extensions.IntExt.formatNumberToString
import com.example.feature.repos.domain.model.ReposModel
import com.example.feature.repos.domain.model.ReposOwnerModel

@Composable
fun ReposListItem(
    reposModel: ReposModel,
    navController: NavController
) {
    val pulls = "pulls/"
    Box(
        modifier = Modifier
            .padding(8.dp)
            .border(width = 0.5.dp, color = MaterialTheme.colorScheme.surfaceVariant)
            .clickable {
                navController.navigate("$pulls${reposModel.owner.login}/${reposModel.name}")
            }
    ) {
        ListItem(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(130.dp),
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.background,
            ),
            overlineContent = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = reposModel.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            headlineContent = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = reposModel.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    minLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            },
            supportingContent = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    SupportingInfo(
                        onClick = { },
                        text = reposModel.forksCount.formatNumberToString(),
                        icon = painterResource(R.drawable.code_fork),
                        contentDescription = stringResource(R.string.forks_number)
                    )
                    SupportingInfo(
                        onClick = { },
                        text = reposModel.stargazersCount.formatNumberToString(),
                        icon = painterResource(R.drawable.filled_star),
                        contentDescription = stringResource(R.string.stars_number)
                    )
                }
            },
            trailingContent = {
                OwnerSection(reposModel.owner)
            }
        )
    }
}

@Composable
fun SupportingInfo(
    onClick: () -> Unit,
    text: String,
    icon: Painter,
    contentDescription: String
) {
    AssistChip(
        onClick = onClick,
        label = { Text(text) },
        leadingIcon = {
            Image(
                painter = icon,
                contentDescription = contentDescription,
                modifier = Modifier.size(AssistChipDefaults.IconSize)
            )
        },
        border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier
            .padding(end = 16.dp)
    )
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
fun OwnerSection(owner: ReposOwnerModel) {
    Box(
        Modifier
            .fillMaxHeight()
            .padding(vertical = 12.dp)
            .width(120.dp)
    ) {
        Card(
            modifier = Modifier.align(Alignment.TopCenter).size(58.dp),
            shape = CircleShape,
            elevation = CardDefaults.cardElevation(1.dp)
        ) {
            GlideImage(
                model = owner.avatarUrl,
                contentDescription = stringResource(id = R.string.avatar_of, owner.login),
            )
        }
        Text(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = owner.login,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Preview(showBackground = false)
@Composable
fun ReposListItemPreview() {
    ReposListItem(
        ReposModel(
            name = "GitHub API Android",
            description = "App Android para mostrar el contenido de la API de GitHub aepofkjapfeokpffkekfefff123f",
            stargazersCount = 100000,
            forksCount = 500000,
            owner = ReposOwnerModel(
                login = "camilahurtadoc",
                avatarUrl = "https://avatars.githubusercontent.com/u/193549342?v=4"
            )
        ),
        rememberNavController()
    )
}