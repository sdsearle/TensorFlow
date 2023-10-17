package com.example.composebackbone

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composebackbone.screens.ExampleScreenTwo
import com.example.composebackbone.screens.HomeScreen

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
            HomeScreen(root)
        }
        composable("detail") {
            ExampleScreenTwo(root)
        }
    }
}


