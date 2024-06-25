package com.example.tensorflow

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tensorflow.screens.ExampleScreenTwo
import com.example.tensorflow.screens.HomeScreen
import com.example.tensorflow.screens.SpeechToResultsWithTensorFlow

/**
 * Created by Spencer Searle, github: sdsearle on 9/15/2023.
 */

val navController: NavHostController
    @Composable
    get() = rememberNavController()

@Composable
fun CreateNavHost(){
    val root = navController
    NavHost(navController = root, startDestination = "home") {
        composable("home") {
            //val vm: ExampleViewModel by viewModel()
            SpeechToResultsWithTensorFlow(root)
        }
        composable("detail") {
            ExampleScreenTwo(root)
        }
    }
}


