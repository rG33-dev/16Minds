package com.example.whu.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whu.domain.CalculatePersonalityUseCase
import com.example.whu.model.PersonalityResult
import com.example.whu.model.Question
import com.example.whu.repository.QuestionRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ViewModel for the Personality Test app.
 * Manages state including welcome and instruction phases.
 */
class PersonalityViewModel(
    private val repository: QuestionRepository,
    private val calculatePersonalityUseCase: CalculatePersonalityUseCase
) : ViewModel() {

    private val _currentScreen = mutableStateOf("welcome")
    val currentScreen: State<String> = _currentScreen

    private val _currentPage = mutableIntStateOf(0)
    val currentPage: State<Int> = _currentPage

    private val _answers = mutableStateMapOf<Int, Int>()
    val answers: Map<Int, Int> = _answers

    private val _questions = mutableStateOf(repository.getQuestions())
    val questions: State<List<Question>> = _questions

    private val _result = mutableStateOf<PersonalityResult?>(null)
    val result: State<PersonalityResult?> = _result

    private val pageSize = 5

    fun startToInstructions() {
        _currentScreen.value = "instructions"
    }

    fun startQuiz() {
        _currentScreen.value = "home"
    }

    fun updateAnswer(questionId: Int, score: Int) {
        _answers[questionId] = score
    }

    fun nextPage() {
        val totalPages = (questions.value.size + pageSize - 1) / pageSize
        if (_currentPage.intValue < totalPages - 1) {
            _currentPage.intValue++
        } else {
            processResults()
        }
    }

    fun previousPage() {
        if (_currentPage.intValue > 0) {
            _currentPage.intValue--
        } else {
            _currentScreen.value = "instructions"
        }
    }

    private fun processResults() {
        viewModelScope.launch {
            _currentScreen.value = "loading"
            delay(2500)
            
            questions.value.forEach { q ->
                if (!_answers.containsKey(q.id)) {
                    _answers[q.id] = 5
                }
            }
            
            _result.value = calculatePersonalityUseCase.execute(_questions.value, _answers)
            _currentScreen.value = "result"
        }
    }

    fun resetQuiz() {
        _answers.clear()
        _currentPage.intValue = 0
        _result.value = null
        _currentScreen.value = "welcome"
    }

    fun getQuestionsForCurrentPage(): List<Question> {
        val start = _currentPage.intValue * pageSize
        val end = minOf(start + pageSize, questions.value.size)
        return if (start < questions.value.size) {
            questions.value.subList(start, end)
        } else {
            emptyList()
        }
    }

    fun isLastPage(): Boolean {
        val totalPages = (questions.value.size + pageSize - 1) / pageSize
        return _currentPage.intValue == totalPages - 1
    }
}
