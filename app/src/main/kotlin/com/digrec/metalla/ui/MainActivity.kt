package com.digrec.metalla.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.digrec.metalla.core.ui.theme.MetallaTheme

/** Created by Dejan Igrec */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent { MetallaTheme { MetallaApp() } }
    }
}
