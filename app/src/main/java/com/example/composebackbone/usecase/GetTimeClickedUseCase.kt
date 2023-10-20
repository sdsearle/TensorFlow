package com.example.composebackbone.usecase

import com.example.composebackbone.repo.ExampleRepo
import javax.inject.Inject

class GetTimeClickedUseCase @Inject constructor(private val repo: ExampleRepo) {
    fun invoke() = repo.timeClicked
}