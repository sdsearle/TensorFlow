package com.example.composebackbone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import com.example.composebackbone.ui.theme.ComposeBackBoneTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                ComposeBackBoneTheme(darkMode = isSystemInDarkTheme()) {
                    CreateNavHost()
                }
        }
    }

}

