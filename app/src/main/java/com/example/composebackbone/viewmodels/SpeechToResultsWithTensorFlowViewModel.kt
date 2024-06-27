/**
 * Created by Spencer Searle on 6/24/24.
 */

package com.example.tensorflow.viewmodels

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import com.example.composebackbone.models.DataSet
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
import org.tensorflow.lite.task.text.qa.BertQuestionAnswerer
import java.io.IOException
import java.io.InputStream
import java.util.Locale

@HiltViewModel
class SpeechToResultsWithTensorFlowViewModel @Inject constructor(@ApplicationContext val context: Context): ViewModel() {
    val text = mutableStateOf("")
    val endResults = mutableStateOf(listOf<String>())
    val bertResults = mutableStateListOf<String>()
    var isRecording = mutableStateOf(false)
    private  val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
    private val recognitionIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
    private lateinit var interpreter: Interpreter

    private lateinit var bertClassifier: BertNLClassifier
    private lateinit var nlClassifier: NLClassifier
    private val currentModel = MOBILEBERT
    private val currentDelegate = DELEGATE_CPU
    private lateinit var executor: ScheduledThreadPoolExecutor

    private var bertQuestionAnswerer: BertQuestionAnswerer? = null

    var numThreads: Int = 2

    private val labelDictionary = hashMapOf<Int, String>(
        0 to "Location",
        1 to "Asset",
        2 to "Workspace"
    )

    private val file = loadJson()



    private val contextDictionary = hashMapOf<Int, List<String>>(
        0 to (file?.getLocations() ?: listOf("ERROR")),
        1 to (file?.getAssets() ?: listOf("ERROR")),
        2 to (file?.getWorkspaces() ?: listOf("ERROR")),
    )

    companion object {
        const val DELEGATE_CPU = 0
        const val DELEGATE_NNAPI = 1
        const val DELEGATE_GPU = 2
        const val WORD_VEC = "wordvec.tflite"
        const val MOBILEBERT = "mobilebert.tflite"
        private const val BERT_QA_MODEL = "models_tflite_task_library_bert_qa_lite-model_mobilebert_1_metadata_1.tflite"
        const val CONTEXT_FILE = "context.json"
    }

    init {
        initSpeechRecognizer()
        initClassifier()
    }

    private fun initSpeechRecognizer() {
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
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
                Handler(Looper.getMainLooper()).postDelayed({
                    //Do something after 100ms
                    isRecording.value = false
                }, 3000)
            }

            override fun onError(error: Int) {
                if (error == 5){
                    return
                }
                text.value = "Error: $error"
                isRecording.value = false
            }

            override fun onResults(results: Bundle?) {
                val data = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                data?.map { Timber.d(it) }
                text.value = data?.joinToString { it -> it } ?: ""
                text.value = text.value.lowercase(Locale.getDefault())
                /*newTextList.clear()
                viewModel.onTranscribeStopped()
                val currentText = binding.inputField.text.toString()
                if (currentText.isNotEmpty()) {
                    viewModel.getSuggestedTaskProperties(binding.inputField.text.toString())
                }*/

                Handler(Looper.getMainLooper()).postDelayed({
                    //Do something after 100ms
                   isRecording.value = false
                }, 3000)
            }

            override fun onPartialResults(p0: Bundle?) {
                Timber.d("onPartialResults")
            }

            override fun onEvent(p0: Int, p1: Bundle?) {
                Timber.d("onEvent")
            }
        })
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

    private fun setupBertQuestionAnswerer() {
        val baseOptionsBuilder = BaseOptions.builder().setNumThreads(numThreads)

        when (currentDelegate) {
            DELEGATE_CPU -> {
                // Default
            }
            DELEGATE_GPU -> {
                /*if (CompatibilityList().isDelegateSupportedOnThisDevice) {
                    baseOptionsBuilder.useGpu()
                } else {
                    answererListener?.onError("GPU is not supported on this device")
                }*/
                //TODO MAKE THE ABOVE WORK
            }
            DELEGATE_NNAPI -> {
                baseOptionsBuilder.useNnapi()
            }
        }

        val options = BertQuestionAnswerer.BertQuestionAnswererOptions.builder()
            .setBaseOptions(baseOptionsBuilder.build())
            .build()

        try {
            bertQuestionAnswerer =
                BertQuestionAnswerer.createFromFileAndOptions(context, BERT_QA_MODEL, options)
        } catch (e: IllegalStateException) {
            Timber.e("Bert Question Answerer failed to initialize. See error logs for details")
            Timber.e( "TFLite failed to load model with error: " + e.message)
        }
    }

    fun answer(contextOfQuestion: String, question: String, value: String, key: Int) {
        if (bertQuestionAnswerer == null) {
            setupBertQuestionAnswerer()
        }

        // Inference time is the difference between the system time at the start and finish of the
        // process
        var inferenceTime = SystemClock.uptimeMillis()

        val answers = bertQuestionAnswerer?.answer(contextOfQuestion, question)
        inferenceTime = SystemClock.uptimeMillis() - inferenceTime
        val temp = answers?.firstOrNull()?.text ?: "NA"
        val cleanAnswer = "$value: " + cleanupAnswer(temp, key)
        bertResults.add(cleanAnswer)
    }

    private fun cleanupAnswer(temp: String, key: Int): String {
        var split = temp.split(",")
        var searchable = split.first().trim()
        if (searchable.contains("-")) {
            split = searchable.split("-")
            searchable = split.first() + "-"
        }
        var foundString = contextDictionary.getOrDefault(key, listOf("ERROR")).firstOrNull { it.contains(searchable, true) } ?: "ERROR"
        return foundString?.split("-")?.first() ?: "ERROR"

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

            endResults.value = results.map {
                val string = "${it.label} ${it.score}"
                string }

            Timber.d("End results size ${endResults.value.size}")
            //listener.onResult(results, inferenceTime)
        }
    }

    fun handleRecording() {
        Timber.d("Handle Recording")
        if (!isRecording.value){
            Timber.d("Start Recording")
            speechRecognizer.startListening(recognitionIntent)
        }else{
            Timber.d("Stop Recording")
            speechRecognizer.stopListening()
        }
        isRecording.value = !isRecording.value
    }

    fun handleTensorFlow() {
        //classify(text.value)
        bertResults.clear()
        labelDictionary.forEach {
            val question = "${text.value}?"
            answer(contextDictionary.getOrDefault(it.key, listOf("ERROR")).joinToString(), question, it.value, it.key)
        }
    }

    fun loadJson(): DataSet? {
        var dataSet: DataSet? = null
        try {
            val inputStream: InputStream = context.assets.open(CONTEXT_FILE)
            val bufferReader = inputStream.bufferedReader()
            val stringJson: String = bufferReader.use { it.readText() }
            val datasetType = object : TypeToken<DataSet>() {}.type
            dataSet = Gson().fromJson(stringJson, datasetType)
        } catch (e: IOException) {
            Timber.e(e.message.toString())
        }
        return dataSet
    }

}