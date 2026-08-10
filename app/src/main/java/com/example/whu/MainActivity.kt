package com.example.whu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.whu.model.navigation.PersonalityAppContainer
import com.example.whu.ui.theme.*

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
                        Box(
                            modifier = Modifier
                                .padding(top = 80.dp, bottom = 40.dp)
                                .padding(horizontal = 32.dp)
                                .fillMaxHeight(0.85f)
                                .fillMaxWidth()
                                .widthIn(max = 400.dp)
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
