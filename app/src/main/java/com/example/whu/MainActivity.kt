package com.example.whu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.whu.domain.CalculatePersonalityUseCaseImpl
import com.example.whu.model.navigation.PersonalityApp
import com.example.whu.repository.StaticQuestionRepository
import com.example.whu.ui.HomeScreen
import com.example.whu.ui.ResultScreen
import com.example.whu.ui.theme.WhuTheme
import com.example.whu.viewmodel.PersonalityViewModel
import com.example.whu.viewmodel.PersonalityViewModelFactory

/**
 * MainActivity: Refactored for cleanliness and SOLID principles.
 * Acts as the Composition Root and handles navigation.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Dependency Inversion: Injecting concrete implementations via Factory
        val repository = StaticQuestionRepository()
        val calculatePersonalityUseCase = CalculatePersonalityUseCaseImpl()
        val factory = PersonalityViewModelFactory(repository, calculatePersonalityUseCase)
        val viewModel = ViewModelProvider(this, factory)[PersonalityViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            WhuTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PersonalityApp(viewModel)
                }
            }
        }
    }
}


