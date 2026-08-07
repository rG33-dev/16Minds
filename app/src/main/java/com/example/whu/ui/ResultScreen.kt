package com.example.whu.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.example.whu.R
import com.example.whu.model.PersonalityResult
import com.example.whu.ui.theme.*

/**
 * ResultScreen displays the magic personality outcome.
 * It is aligned in a central column for a mobile feel and features a joyous Lottie animation.
 */
@Composable
fun ResultScreen(result: PersonalityResult?, onReset: () -> Unit) {
    if (result == null) return

    val backgroundBrush = if (isSystemInDarkTheme()) {
        Brush.verticalGradient(BackgroundGradientDark)
    } else {
        Brush.verticalGradient(BackgroundGradientLight)
    }

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundBrush),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.statusBarsPadding())
            Spacer(modifier = Modifier.height(40.dp))

            // --- ANIMATION SPACE ---
            // Replaced the Star Icon with the Lottie 'search' animation
            ResultAnimationPlaceholder(result.mbtiType)
            // ------------------------

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "MAGIC UNLOCKED!",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.sp
                ),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Primary Identity Card
            ResultSectionCard(delay = 150) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                listOf(JoyLightPrimary, MagicPurple)
                            )
                        )
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = result.mbtiType,
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = 8.sp
                        ),
                        color = Color.White
                    )
                    Text(
                        text = "THE ${result.nickname.uppercase()}",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.White.copy(alpha = 0.9f),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Jungian Insight Card
            ResultSectionCard(delay = 300) {
                Column(modifier = Modifier.padding(28.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            modifier = Modifier.size(12.dp),
                            shape = RoundedCornerShape(6.dp),
                            color = JoyLightSecondary
                        ) {}
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Jungian Superpower",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = result.jungType,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Black),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Descriptive Card
            ResultSectionCard(delay = 450) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(28.dp)
                ) {
                    Text(
                        text = result.description,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            lineHeight = 30.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Action Button
            Button(
                onClick = onReset,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp)
                    .shadow(16.dp, RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SuccessGreen,
                    contentColor = Color.White
                )
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(28.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    "ANOTHER ADVENTURE!",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black
                )
            }

            Spacer(modifier = Modifier.height(64.dp))
            Spacer(modifier = Modifier.navigationBarsPadding())
        }
    }
}

/**
 * Plays the 'search' Lottie animation as requested.
 */
@Composable
fun ResultAnimationPlaceholder(type: String) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.search))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    Box(
        modifier = Modifier
            .height(220.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(200.dp)
        )
        Text(
            text = "✨ $type ✨",
            style = MaterialTheme.typography.labelLarge,
            color = MagicPurple,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun ResultSectionCard(
    delay: Int,
    content: @Composable () -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { it / 3 },
            animationSpec = tween(700, delayMillis = delay)
        ) + fadeIn(animationSpec = tween(700, delayMillis = delay))
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(32.dp),
                    spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
                ),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            content = { content() }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ResultPreview() {
    WhuTheme {
        ResultScreen(
            result = PersonalityResult(
                mbtiType = "ENFP",
                jungType = "Extraverted Intuition",
                nickname = "Sparkling Dreamer",
                description = "You are full of energy and magic!"
            ),
            onReset = {}
        )
    }
}
