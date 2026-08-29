package com.digrec.metalla.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.digrec.metalla.R
import com.digrec.metalla.core.ui.theme.MetallaTheme
import com.digrec.metalla.ui.navigation.MetallaDestination
import com.digrec.metalla.ui.navigation.MetallaNavHost

@Composable
fun MetallaApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    MetallaAppContent(
        modifier = modifier,
        currentDestination = currentDestination,
        onNavigate = { destination ->
            navController.navigate(destination) {
                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        },
    ) { innerPadding ->
        MetallaNavHost(navController, modifier = Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetallaAppContent(
    currentDestination: NavDestination?,
    onNavigate: (MetallaDestination) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
) {
    val items = remember {
        listOf(
            NavigationItem(MetallaDestination.Home, R.string.title_home, Icons.Default.Home),
            NavigationItem(
                MetallaDestination.Dashboard,
                R.string.title_dashboard,
                Icons.Default.Dashboard,
            ),
            NavigationItem(
                MetallaDestination.Settings,
                R.string.title_settings,
                Icons.Default.Settings,
            ),
        )
    }

    val currentItem = items.find { item ->
        currentDestination?.hasRoute(item.destination::class) == true
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(currentItem?.resourceId ?: R.string.app_name)) },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    ),
            )
        },
        bottomBar = {
            NavigationBar {
                items.forEach { item ->
                    val selected =
                        currentDestination?.hierarchy?.any {
                            it.hasRoute(item.destination::class)
                        } == true
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(stringResource(item.resourceId)) },
                        selected = selected,
                        onClick = { onNavigate(item.destination) },
                    )
                }
            }
        },
    ) { innerPadding ->
        content(innerPadding)
    }
}

@Preview(showBackground = true)
@Composable
fun MetallaAppPreview() {
    MetallaTheme {
        MetallaAppContent(
            currentDestination = null,
            onNavigate = {},
        ) { innerPadding ->
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "App Preview Content")
            }
        }
    }
}

private data class NavigationItem(
    val destination: MetallaDestination,
    val resourceId: Int,
    val icon: ImageVector,
)
