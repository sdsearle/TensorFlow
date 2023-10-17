package com.example.composebackbone.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composebackbone.viewmodels.ExampleViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.sqrt

/**
 * Created by Spencer Searle, github: sdsearle on 9/15/2023.
 */

@Composable
fun ExampleScreenTwo(navController: NavController, vm: ExampleViewModel = hiltViewModel()) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = "key") {
        scope.launch {
            vm.getPokemon(vm.colors.keys.first())
        }
    }
    ConstraintLayout() {
        val (row, column) = createRefs()
        Card (modifier = Modifier.constrainAs(row){
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            height = Dimension.fillToConstraints
        }){
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),

            ) {
                vm.colors.forEach {
                    item {
                        ElevatedButton(
                            onClick = {
                                CoroutineScope(Dispatchers.IO).launch {
                                    vm.getPokemon(it.key)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = it.value)
                        ){
                            Text(text = it.key, fontSize = TextUnit(32f, TextUnitType.Sp), color = getTextColor(it.value))
                        }
                    }
                }
            }
        }

        LazyColumn(modifier = Modifier.constrainAs(column){
            top.linkTo(row.bottom)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            height = Dimension.preferredWrapContent
        }) {
            vm.apiString.value?.pokemons?.forEach {
                item {
                Card(modifier = Modifier.fillParentMaxWidth()) {
                    Text(text = it.name, fontSize = TextUnit(32f, TextUnitType.Sp), modifier = Modifier.padding(16.dp)) }
                }

            }
        }
    }


}

fun getTextColor(value: Color): Color {
    val l = value.luminance()
    return if (l > sqrt(1.05 * 0.05) - 0.05){
        Color.Black
    } else{
        Color.White
    }

}

