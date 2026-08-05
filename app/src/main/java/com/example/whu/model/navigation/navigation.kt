package com.example.whu.model.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.example.whu.ui.HomeScreen
import com.example.whu.ui.ResultScreen
import com.example.whu.viewmodel.PersonalityViewModel

@Composable
fun PersonalityApp(viewModel: PersonalityViewModel) {
    val currentScreen by viewModel.currentScreen
    val currentPage by viewModel.currentPage
    val questions = viewModel.getQuestionsForCurrentPage()
    val totalQuestions = viewModel.questions.value.size
    val answers = viewModel.answers
    val result by viewModel.result
    val isLastPage = viewModel.isLastPage()

    AnimatedContent(
        targetState = currentScreen,
        transitionSpec = {
            fadeIn(animationSpec = tween(500)) togetherWith fadeOut(animationSpec = tween(500))
        },
        label = "ScreenTransition"
    ) { screen ->
        when (screen) {
            "home" -> HomeScreen(
                questions = questions,
                pageIndex = currentPage,
                totalQuestions = totalQuestions,
                answers = answers,
                isLastPage = isLastPage,
                onAnswerChange = { id, score -> viewModel.updateAnswer(id, score) },
                onNext = { viewModel.nextPage() },
                onBack = { viewModel.previousPage() }
            )
            "result" -> ResultScreen(
                result = result,
                onReset = { viewModel.resetQuiz() }
            )
        }
    }
}