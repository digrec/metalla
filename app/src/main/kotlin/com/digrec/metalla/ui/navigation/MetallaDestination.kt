package com.digrec.metalla.ui.navigation

import kotlinx.serialization.Serializable

/** Type-safe navigation destinations for the app. */
sealed interface MetallaDestination {
    @Serializable data object Home : MetallaDestination

    @Serializable data object Dashboard : MetallaDestination

    @Serializable data object Settings : MetallaDestination
}
