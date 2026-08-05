package com.example.whu.model

/**
 * SRP: Represents only the data structure of a question.
 */
data class Question(
    val id: Int,
    val text: String,
    val category: QuestionCategory
)

enum class QuestionCategory {
    E, S, T, J
}

/**
 * Data model for the test result.
 */
data class PersonalityResult(
    val mbtiType: String,
    val jungType: String,
    val nickname: String,
    val description: String
)
