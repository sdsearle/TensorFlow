/**
 * Created by Spencer Searle on 6/24/24.
 */

package com.example.tensorflow.viewmodels

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.task.core.BaseOptions
import timber.log.Timber
import javax.inject.Inject
import org.tensorflow.lite.task.text.nlclassifier.BertNLClassifier
import org.tensorflow.lite.task.text.nlclassifier.NLClassifier
import java.util.concurrent.ScheduledThreadPoolExecutor
import org.tensorflow.lite.support.label.Category

@HiltViewModel
class SpeechToResultsWithTensorFlowViewModel @Inject constructor(@ApplicationContext val context: Context): ViewModel() {
    val text = mutableStateOf("")
    val endResults = mutableStateOf("")
    var isRecording = mutableStateOf(false)
    val bool = mutableStateOf("${isRecording.value}")
    private  val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
    private val recognitionIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
    private lateinit var interpreter: Interpreter

    private lateinit var bertClassifier: BertNLClassifier
    private lateinit var nlClassifier: NLClassifier
    private val currentModel = MOBILEBERT
    private val currentDelegate = DELEGATE_CPU
    private lateinit var executor: ScheduledThreadPoolExecutor

    private val labelDictionary = hashMapOf<Int, String>(
        0 to "Location",
        1 to "Type",
        2 to "What"
    )

    companion object {
        const val DELEGATE_CPU = 0
        const val DELEGATE_NNAPI = 1
        const val WORD_VEC = "wordvec.tflite"
        const val MOBILEBERT = "mobilebert.tflite"
    }

    init {
        speechRecognizer.setRecognitionListener(object :RecognitionListener{
            override fun onReadyForSpeech(p0: Bundle?) {
                Timber.d("Ready")
            }

            override fun onBeginningOfSpeech() {
                Timber.d("onBeginningOfSpeech")
            }

            override fun onRmsChanged(p0: Float) {
                Timber.d("onRmsChanged")
            }

            override fun onBufferReceived(p0: ByteArray?) {
                Timber.d("onBufferReceived")
            }

            override fun onEndOfSpeech() {
                Timber.d("onEndOfSpeech")
            }

            override fun onError(error: Int) {
                text.value = "Error: $error"
            }

            override fun onResults(results: Bundle?) {
                val data = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                data?.map { Timber.d(it) }
                text.value = data?.joinToString { it -> it } ?: ""
                /*newTextList.clear()
                viewModel.onTranscribeStopped()
                val currentText = binding.inputField.text.toString()
                if (currentText.isNotEmpty()) {
                    viewModel.getSuggestedTaskProperties(binding.inputField.text.toString())
                }*/
            }

            override fun onPartialResults(p0: Bundle?) {
                Timber.d("onPartialResults")
            }

            override fun onEvent(p0: Int, p1: Bundle?) {
                Timber.d("onEvent")
            }
        })
        initClassifier()
    }

    fun initClassifier() {
        val baseOptionsBuilder = BaseOptions.builder()

        // Use the specified hardware for running the model. Default to CPU.
        // Possible to also use a GPU delegate, but this requires that the classifier be created
        // on the same thread that is using the classifier, which is outside of the scope of this
        // sample's design.
        when (currentDelegate) {
            DELEGATE_CPU -> {
                // Default
            }
            DELEGATE_NNAPI -> {
                baseOptionsBuilder.useNnapi()
            }
        }

        val baseOptions = baseOptionsBuilder.build()

        // Directions for generating both models can be found at
        // https://www.tensorflow.org/lite/models/modify/model_maker/text_classification
        if( currentModel == MOBILEBERT ) {
            val options = BertNLClassifier.BertNLClassifierOptions
                .builder()
                .setBaseOptions(baseOptions)
                .build()

            bertClassifier = BertNLClassifier.createFromFileAndOptions(
                context,
                MOBILEBERT,
                options)
        } else if (currentModel == WORD_VEC) {
            val options = NLClassifier.NLClassifierOptions.builder()
                .setBaseOptions(baseOptions)
                .build()

            nlClassifier = NLClassifier.createFromFileAndOptions(
                context,
                WORD_VEC,
                options)
        }
    }

    fun initAnotherClassifier(){
        interpreter = Interpreter(loadModel())
    }

    fun classify(text: String) {
        executor = ScheduledThreadPoolExecutor(1)

        executor.execute {
            val results: List<Category>
            // inferenceTime is the amount of time, in milliseconds, that it takes to
            // classify the input text.
            var inferenceTime = SystemClock.uptimeMillis()
            Timber.d("Start Time: $inferenceTime")

            // Use the appropriate classifier based on the selected model
            if(currentModel == MOBILEBERT) {
                results = bertClassifier.classify(text)
            } else {
                results = nlClassifier.classify(text)
            }

            inferenceTime = SystemClock.uptimeMillis() - inferenceTime
            Timber.d("End Time: $inferenceTime")

            endResults.value = results.joinToString()

            //listener.onResult(results, inferenceTime)
        }
    }

    fun handleRecording() {
        Timber.d("Handle Recording")
        if (isRecording.value){
            Timber.d("Start Recording")
            speechRecognizer.startListening(recognitionIntent)
        }else{
            Timber.d("Stop Recording")
            speechRecognizer.stopListening()
        }
        isRecording.value = !isRecording.value
    }

    fun handleTensorFlow() {
        classify(text.value)
    }

}