/**
 * Created by Spencer Searle on 6/24/24.
 */

package com.example.tensorflow.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material.icons.rounded.Assistant
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.MicOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tensorflow.R
import com.example.tensorflow.ui.theme.myBorders
import com.example.tensorflow.ui.theme.myGray
import com.example.tensorflow.ui.theme.myLightGray
import com.example.tensorflow.viewmodels.SpeechToResultsWithTensorFlowViewModel


@Composable
fun SpeechToResultsWithTensorFlow(navController: NavController, vm: SpeechToResultsWithTensorFlowViewModel = hiltViewModel()) {
    val context = LocalContext.current
    //Do permission check for recording
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        //val (recBtn, subBtn, recText, speechTexts, resultsText) = createRefs()
        //val (buttons, speechText, resultText) = createRefs()
        lateinit var constraints: ConstraintSet
        val isRecordingText = if (vm.isRecording.value) {
            "Recording"
        } else {
            ""
        }

        BoxWithConstraints {
            constraints = if (minWidth < 600.dp) {
                decoupledConstraints(margin = 16.dp) // Portrait constraints
            } else {
                decoupledConstraints(margin = 32.dp) // Landscape constraints
            }

            Column(modifier = Modifier
                .fillMaxSize(),
                //.background(Color.Red),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box (
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(8.dp),
                ) {
                    Text(
                        isRecordingText,
                        color = Color.Red,
                        modifier = Modifier
                    )
                }
                Box (
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(15.dp)
                        .size(600.dp)
                        .clip(MaterialTheme.shapes.small)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.shapes.small
                        )
                        .background(myLightGray),
                ) {
                    //vm.text.value,
                    Text(
                        text = vm.text.value,//"Text tkvnwevewkvrivrivrivrioboib eifwe iof eifiihgiori4 i ih rihirh exceowevowiowff oihfoiwehgoiwf hofhwoifhwioefiowefoi oifhwoifhw4iof ofweoifhw4 eiofwoifhwe",
                        modifier = Modifier
                            .padding(6.dp)
                    )

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .height(130.dp)
                            .clip(MaterialTheme.shapes.small)
                            .background(myGray),
                    ) {
                        ResultPrompt(vm, constraints)
                    }
                }


                //RecordStateText(vm, constraints)
                //ResultPrompt(vm, constraints)
                RecordAndSubmitBtn(vm, constraints)
            }
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
            val speechText = createRefFor("speechText")
            val resultText = createRefFor("resultText")
            val buttons = createRefFor("buttons")

            constrain(speechText) {
                /*top.linkTo(parent.top, margin)
                start.linkTo(parent.start)
                end.linkTo(parent.end)*/
            }
            constrain(resultText) {
                //top.linkTo(speechText.top, 15.dp)
                //top.linkTo(speechText.bottom, margin)
                /*start.linkTo(parent.start)
                end.linkTo(parent.end)*/
            }
            constrain(buttons) {
                /*start.linkTo(parent.start)
                end.linkTo(parent.end)*/
            }
    }
}

@Composable
private fun RecordStateText(vm: SpeechToResultsWithTensorFlowViewModel, constraints: ConstraintSet) {
    val isRecordingText = if (vm.isRecording.value) {
        "Recording"
    } else {
        ""
    }

    ConstraintLayout(constraints) {
        Column(
            modifier = Modifier
                .layoutId("speechText"),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = vm.text.value//"Text tkvnwevewkvrivrivrivrioboib eifwe iof eifiihgiori4 i ih rihirh exceowevowiowff oihfoiwehgoiwf hofhwoifhwioefiowefoi oifhwoifhw4iof ofweoifhw4 eiofwoifhwe",
                )
        }

    }


    /*Text(
        text = vm.text.value,
        modifier = Modifier
    )*/

    /*Text(text = vm.isRecording.value.toString(),
            modifier = Modifier.constrainAs(debug){
            top.linkTo(button2.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
                PaddingValues(16.dp)
            height = Dimension.fillToConstraints
        })
        Text(text = vm.text.value,
            modifier = Modifier.constrainAs(speechText){
                top.linkTo(debug.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                PaddingValues(16.dp)
                height = Dimension.fillToConstraints
            })*/
}

@Composable
private fun ResultPrompt(vm: SpeechToResultsWithTensorFlowViewModel, constraints: ConstraintSet) {
    val list = listOf(1)
    val textValue = "Location: Park\nAsset: Oven\nWorkspace: Make Ready"

    ConstraintLayout(constraints) {
        LazyColumn(
            modifier = Modifier
                .layoutId("resultText")
                .fillMaxWidth(),
                //.background(Color.Red),
                //.padding(5.dp),
        ) {
            vm.bertResults.forEach { value ->
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .padding(
                                horizontal = 8.dp,
                                vertical = 4.dp,
                            )
                            .clip(RoundedCornerShape(5.dp))
                            //.background(MaterialTheme.colorScheme.primary),
                    ){
                        Row {

                            val dynamicPic = when(vm.bertResults.indexOf(value)) {
                                0 -> R.drawable.location_on_24dp_fill0_wght400_grad0_opsz24
                                1 -> R.drawable.bathtub_24dp_fill0_wght400_grad0_opsz24
                                2 -> R.drawable.construction_24dp_fill0_wght400_grad0_opsz24
                                else -> {
                                    R.drawable.neurology_24dp_fill0_wght400_grad0_opsz24
                                }
                            }

                            Image(
                                painter = painterResource(id = dynamicPic),
                                contentDescription = "AI",
                                modifier = Modifier
                                    .size(20.dp)
                                    .background(Color.Black, Shapes().small)
                                    .clip(CircleShape),
                                //border(dimensionResource(R.dimen.space_2dp), colorScheme.primary)
                            )
                            SetSpacer(dimension = 4.dp)
                            Text(
                                text = value,
                                fontSize = TextUnit(20f, TextUnitType.Sp),
                                modifier = Modifier
                            )
                        }
                    }
                }
            }
    }


        /*vm.bertResults.forEach {
            item {
                Text(
                    text = it,
                    fontSize = TextUnit(32f, TextUnitType.Sp),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }*/

        /*LazyColumn(
            modifier = Modifier
                .background(Color.LightGray, MaterialTheme.shapes.large)
                .constrainAs(resultsText) {
                    top.linkTo(speechText.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    PaddingValues(16.dp)
                    height = Dimension.fillToConstraints
                }) {
            vm.bertResults.forEach {
                Timber.d("ZOL Building List")
                item {
                        Text(
                            text = it,
                            fontSize = TextUnit(32f, TextUnitType.Sp),
                            modifier = Modifier.padding(16.dp)
                        )
                }
            }
        }*/

    }
}

@Composable
private fun RecordAndSubmitBtn(vm: SpeechToResultsWithTensorFlowViewModel, constraints: ConstraintSet) {
    val recBtnColor = if (vm.isRecording.value) {
        colorScheme.primary
    } else {
        Color.LightGray
    }

    val recBtnIcon = if (vm.isRecording.value) {
        Icons.Rounded.Mic
    } else {
        Icons.Rounded.MicOff
    }

    ConstraintLayout(constraints) {
        Row(
            modifier = Modifier
                .layoutId("buttons")
                .padding(10.dp)
                .height(50.dp),
                //.background(Color.Blue),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            //Record Button
            Card(
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp)
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxSize(),
                    onClick = { vm.handleRecording() },
                    shape = MaterialTheme.shapes.small,
                    //contentPadding = PaddingValues(all = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = recBtnColor),
                ) {
                    Icon(imageVector = recBtnIcon, contentDescription = "Record")
                }
            }

            SetSpacer(dimension = 8.dp)

            //Submit Button
            Card(
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp)
            ) {
                Button(
                    modifier = Modifier.fillMaxSize(),
                    onClick = { vm.handleTensorFlow() },
                    shape = MaterialTheme.shapes.small,
                    //contentPadding = PaddingValues(all = 16.dp),
                    //colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                ) {
                    /*Image(
                        painter = painterResource(id = R.drawable.neurology_24dp_fill0_wght400_grad0_opsz24),
                        contentDescription = "AI",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                        //.border(dimensionResource(R.dimen.space_2dp), colorScheme.primary)
                    )*/
                    Icon(imageVector = Icons.Rounded.AutoAwesome, contentDescription = "Submit")
                }
            }
        }
    }
}

@Composable
fun SetSpacer(dimension: Dp) = Spacer(modifier = Modifier.width(dimension))

@Preview(showBackground = true)
@Composable
fun ViewPreview() {
    lateinit var fakeController: NavController
    SpeechToResultsWithTensorFlow(fakeController)
}
