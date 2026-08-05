package com.example.whu.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.whu.domain.CalculatePersonalityUseCase
import com.example.whu.repository.QuestionRepository

class PersonalityViewModelFactory(
    private val repository: QuestionRepository,
    private val calculatePersonalityUseCase: CalculatePersonalityUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersonalityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PersonalityViewModel(repository, calculatePersonalityUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
