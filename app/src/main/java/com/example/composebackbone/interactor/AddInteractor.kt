package com.example.tensorflow.interactor

import com.example.tensorflow.repo.ExampleRepo
import javax.inject.Inject

class AddInteractor @Inject constructor(val repo: ExampleRepo){

    val timeClicked = repo.timeClicked
    fun addClicked() {
        repo.addClicked()
    }
}