package com.example.tensorflow.usecase.add

import com.example.tensorflow.repo.ExampleRepo
import javax.inject.Inject

class AddTimesClickedUseCase @Inject constructor(private val repo: ExampleRepo){
    fun invoke(){
        repo.addClicked()
    }
}