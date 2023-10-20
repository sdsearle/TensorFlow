package com.example.composebackbone.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composebackbone.interactor.AddInteractor
import com.example.composebackbone.interactor.PokemonInteractor
import com.example.composebackbone.repo.ExampleRepo
import com.example.composebackbone.ui.theme.ComposeBackBoneTheme
import com.example.composebackbone.viewmodels.ExampleViewModel

/**
 * Created by Spencer Searle, github: sdsearle on 9/15/2023.
 */

@Composable
fun HomeScreen(navController: NavController, vm: ExampleViewModel = hiltViewModel()) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (button, fab, clickedText) = createRefs()
        Button(
            onClick = {
                vm.clicked(navController)
            },
            modifier = Modifier.constrainAs(button) {
                centerHorizontallyTo(parent)
                centerVerticallyTo(parent)
            }

        ) {
            Text("Go to Detail")
        }
        FloatingActionButton(
            onClick = { vm.add() },
            modifier = Modifier.constrainAs(fab) {
                bottom.linkTo(parent.bottom, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
            }) {
            Icon(Icons.Filled.Add, "Floating action button.")
        }
        Text(text = vm.timesClicked.intValue.toString(),
            modifier = Modifier.constrainAs(clickedText) {
                bottom.linkTo(button.top, margin = 16.dp)
                centerHorizontallyTo(parent)
            }, fontSize = TextUnit(32f, TextUnitType.Sp)
        )

    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeBackBoneTheme {
        HomeScreen(rememberNavController(), ExampleViewModel(PokemonInteractor(ExampleRepo()), AddInteractor(ExampleRepo())))
    }
}