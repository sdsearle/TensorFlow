/**
 * Created by Spencer Searle on 6/24/24.
 */

package com.example.composebackbone.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composebackbone.viewmodels.ExampleViewModel
import com.example.composebackbone.viewmodels.SpeechToResultsWithTensorFlowViewModel

@Composable
fun SpeechToResultsWithTensorFlow(navController: NavController, vm: SpeechToResultsWithTensorFlowViewModel = hiltViewModel()){
    //Do permission check for recording
    ConstraintLayout() {
        val (row, column) = createRefs()
        Button(
            onClick = { vm.handleRecording() },
            shape = MaterialTheme.shapes.large,
            contentPadding = PaddingValues(all = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ){
            Icon(imageVector = Icons.Default.FiberManualRecord, contentDescription = "Record")
        }
        Button(
            onClick = { vm.handleTensorFlow() },
            shape = MaterialTheme.shapes.large,
            contentPadding = PaddingValues(all = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ){
            Icon(imageVector = Icons.Default.FiberManualRecord, contentDescription = "Record")
        }
    }
}