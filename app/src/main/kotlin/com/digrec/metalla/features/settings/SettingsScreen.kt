package com.digrec.metalla.features.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.digrec.metalla.core.ui.theme.MetallaTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val appVersion by viewModel.appVersion.collectAsStateWithLifecycle()
    SettingsContent(appVersion = appVersion, modifier = modifier)
}

@Composable
fun SettingsContent(
    appVersion: String,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = appVersion)
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    MetallaTheme { SettingsContent(appVersion = "v1.0.0+1") }
}
