package com.example.whu.model.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.example.whu.ui.HomeScreen
import com.example.whu.ui.InstructionsScreen
import com.example.whu.ui.LoadingScreen
import com.example.whu.ui.ResultScreen
import com.example.whu.ui.WelcomeScreen
import com.example.whu.PersonalityViewModel

@Composable
fun PersonalityAppContainer(viewModel: PersonalityViewModel) {
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
            fadeIn(animationSpec = tween(600)) togetherWith fadeOut(animationSpec = tween(600))
        },
        label = "AppNavigation"
    ) { screen ->
        when (screen) {
            "welcome" -> WelcomeScreen(
                onContinue = { viewModel.startToInstructions() }
            )
            "instructions" -> InstructionsScreen(
                onStart = { viewModel.startQuiz() }
            )
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
            "loading" -> LoadingScreen()
            "result" -> ResultScreen(
                result = result,
                onReset = { viewModel.resetQuiz() }
            )
        }
    }
}
