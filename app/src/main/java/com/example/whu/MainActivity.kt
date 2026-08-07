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
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.whu.domain.CalculatePersonalityUseCaseImpl
import com.example.whu.model.navigation.PersonalityAppContainer
import com.example.whu.repository.StaticQuestionRepository
import com.example.whu.ui.HomeScreen
import com.example.whu.ui.LoadingScreen
import com.example.whu.ui.ResultScreen
import com.example.whu.ui.theme.*
import com.example.whu.viewmodel.PersonalityViewModel
import com.example.whu.viewmodel.PersonalityViewModelFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = StaticQuestionRepository()
        val calculatePersonalityUseCase = CalculatePersonalityUseCaseImpl()
        val factory = PersonalityViewModelFactory(repository, calculatePersonalityUseCase)
        val viewModel = ViewModelProvider(this, factory)[PersonalityViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            WhuTheme {
                val backgroundBrush = if (isSystemInDarkTheme()) {
                    Brush.verticalGradient(BackgroundGradientDark)
                } else {
                    Brush.verticalGradient(BackgroundGradientLight)
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(backgroundBrush),
                        contentAlignment = Alignment.Center
                    ) {
                        // --- FLOATING MOBILE FRAME ---
                        // Significant padding added to avoid notch and navigation bars
                        Box(
                            modifier = Modifier
                                .padding(top = 80.dp, bottom = 40.dp) // Increased top clearance for notch
                                .padding(horizontal = 32.dp) // More side clearance
                                .fillMaxHeight(0.85f) // Shorter height to float better
                                .fillMaxWidth()
                                .widthIn(max = 400.dp) // Narrower mobile width
                                .shadow(32.dp, RoundedCornerShape(48.dp))
                                .clip(RoundedCornerShape(48.dp))
                                .background(MaterialTheme.colorScheme.surface)
                                .border(
                                    width = 3.dp,
                                    brush = Brush.linearGradient(RainbowGradient),
                                    shape = RoundedCornerShape(48.dp)
                                )
                        ) {
                            PersonalityAppContainer(viewModel)
                        }
                    }
                }
            }
        }
    }
}

