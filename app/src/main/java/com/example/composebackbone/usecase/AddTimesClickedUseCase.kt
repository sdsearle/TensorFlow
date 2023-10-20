package com.example.composebackbone.usecase

import com.example.composebackbone.repo.ExampleRepo
import javax.inject.Inject

class AddTimesClickedUseCase @Inject constructor(private val repo: ExampleRepo){
    fun invoke(){
        repo.addClicked()
    }
}