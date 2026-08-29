package com.digrec.metalla.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.digrec.metalla.features.dashboard.DashboardScreen
import com.digrec.metalla.features.home.HomeScreen
import com.digrec.metalla.features.settings.SettingsScreen

@Composable
fun MetallaNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    homeScreen: @Composable () -> Unit = { HomeScreen() },
    dashboardScreen: @Composable () -> Unit = { DashboardScreen() },
    settingsScreen: @Composable () -> Unit = { SettingsScreen() },
) {
    NavHost(
        navController = navController,
        startDestination = MetallaDestination.Home,
        modifier = modifier,
    ) {
        composable<MetallaDestination.Home> { homeScreen() }
        composable<MetallaDestination.Dashboard> { dashboardScreen() }
        composable<MetallaDestination.Settings> { settingsScreen() }
    }
}
