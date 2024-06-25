/**
 * Created by Spencer Searle on 6/24/24.
 */

package com.example.tensorflow.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkCallingOrSelfPermission
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tensorflow.viewmodels.ExampleViewModel
import com.example.tensorflow.viewmodels.SpeechToResultsWithTensorFlowViewModel
import timber.log.Timber

@Composable
fun SpeechToResultsWithTensorFlow(navController: NavController, vm: SpeechToResultsWithTensorFlowViewModel = hiltViewModel()){
    val context = LocalContext.current
    //Do permission check for recording
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (button1, button2, debug, speechText, resultsText) = createRefs()
        Button(
            onClick = { vm.handleRecording() },
            shape = MaterialTheme.shapes.large,
            contentPadding = PaddingValues(all = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier.constrainAs(button1){
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.fillToConstraints
            }
        ){
            Icon(imageVector = Icons.Default.FiberManualRecord, contentDescription = "Record")
        }
        Button(
            onClick = { vm.handleTensorFlow() },
            shape = MaterialTheme.shapes.large,
            contentPadding = PaddingValues(all = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier.constrainAs(button2){
                top.linkTo(button1.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = androidx.constraintlayout.compose.Dimension.fillToConstraints
            }
        ){
            Icon(imageVector = Icons.Default.Upload, contentDescription = "Submit")
        }
        Text(text = vm.isRecording.value.toString(),
            modifier = Modifier.constrainAs(debug){
            top.linkTo(button2.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
                PaddingValues(16.dp)
            height = androidx.constraintlayout.compose.Dimension.fillToConstraints
        })
        Text(text = vm.text.value,
            modifier = Modifier.constrainAs(speechText){
                top.linkTo(debug.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                PaddingValues(16.dp)
                height = androidx.constraintlayout.compose.Dimension.fillToConstraints
            })
        LazyColumn(modifier = Modifier.constrainAs(resultsText){
            top.linkTo(speechText.bottom)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            PaddingValues(16.dp)
            height = androidx.constraintlayout.compose.Dimension.fillToConstraints
        }){
            vm.bertResults.forEach {
                Timber.d("ZOL Building List")
                item {
                    Card(modifier = Modifier.fillParentMaxWidth()) {
                        Text(
                            text = it,
                            fontSize = TextUnit(32f, TextUnitType.Sp),
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}