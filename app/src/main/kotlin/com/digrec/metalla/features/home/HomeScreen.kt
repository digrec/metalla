package com.digrec.metalla.features.home

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
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val text by viewModel.text.collectAsStateWithLifecycle()
    HomeContent(text = text, modifier = modifier)
}

@Composable
fun HomeContent(
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MetallaTheme { HomeContent(text = "Home Preview") }
}
