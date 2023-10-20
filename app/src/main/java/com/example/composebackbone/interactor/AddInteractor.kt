package com.example.composebackbone.interactor

import com.example.composebackbone.repo.ExampleRepo
import javax.inject.Inject

class AddInteractor @Inject constructor(val repo: ExampleRepo){

    val timeClicked = repo.timeClicked
    fun addClicked() {
        repo.addClicked()
    }
}