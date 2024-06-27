package com.example.tensorflow

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import com.example.tensorflow.ui.theme.tensorflowTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestRecordAudioPermission(this)
        setContent {
                tensorflowTheme(darkMode = isSystemInDarkTheme()) {
                    CreateNavHost()
                }
        }
    }

    fun requestRecordAudioPermission(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val requiredPermission = Manifest.permission.RECORD_AUDIO
            val permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)

            // If the user previously denied this permission then show a message explaining why
            // this permission is needed
            if (PermissionChecker.checkCallingOrSelfPermission(
                    context,
                    requiredPermission
                ) == PermissionChecker.PERMISSION_DENIED) {

                ActivityCompat.requestPermissions(
                    this,
                    permissions,
                    101
                )
            }
        }
    }

}

