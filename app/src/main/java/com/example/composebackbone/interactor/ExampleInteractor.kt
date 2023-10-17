package com.example.composebackbone.interactor

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import javax.inject.Inject

/**
 * Created by Spencer Searle, github: sdsearle on 9/16/2023.
 */

@Composable
fun OnClick() {
    rememberNavController().navigate("detail")
}

