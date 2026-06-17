package ar.edu.unlam.mobile.scaffolding.ui.screens.feed

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ar.edu.unlam.mobile.scaffolding.ui.components.feed.HomeFloatingActionButton
import ar.edu.unlam.mobile.scaffolding.ui.constant.dimension.Dimens.PADDING_MEDIUM

@Composable
fun FeedScreen(onNavigateToCreatePost: () -> Unit) {

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomBar() },
        floatingActionButton = { HomeFloatingActionButton(onNavigateToCreatePost) },
    ) { paddingValues ->

        Content(modifier = Modifier.padding(paddingValues))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar() {

    TopAppBar(
        title = {
            Text(
                text = "Inicio",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        },
        modifier = Modifier.padding(PADDING_MEDIUM)
    )
}

@Composable
private fun Content(modifier: Modifier) {

    Text("Menu principal", modifier = modifier)
}

@Composable
private fun BottomBar() {

    NavigationBar {

        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = {
                Icon(Icons.Default.Home, contentDescription = null)
            },
            label = { BottomBarTextLabel("Inicio") }
        )

        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Icon(Icons.Default.AccountCircle, contentDescription = null)
            },
            label = { BottomBarTextLabel("Perfil") }
        )

        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Icon(Icons.Default.Settings, contentDescription = null)
            },
            label = { BottomBarTextLabel("Ajustes") }
        )
    }
}

@Composable
private fun BottomBarTextLabel(textToShow: String) {

    Text(
        text = textToShow,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurface
    )
}