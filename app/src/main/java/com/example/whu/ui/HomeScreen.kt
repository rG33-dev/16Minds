package com.example.whu.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whu.model.Question
import com.example.whu.ui.theme.*

/**
 * HomeScreen: A premium, paginated quiz screen designed with a mobile-first centered feel.
 */
@Composable
fun HomeScreen(
    questions: List<Question>,
    pageIndex: Int,
    totalQuestions: Int,
    answers: Map<Int, Int>,
    isLastPage: Boolean,
    onAnswerChange: (Int, Int) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    val progress by animateFloatAsState(
        targetValue = ((pageIndex + 1) * 5).coerceAtMost(totalQuestions).toFloat() / totalQuestions,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "progress"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // Joyous Animated Header
        Text(
            text = "✨ Discovery Time! ✨",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Black,
                letterSpacing = 1.sp
            ),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Modern Progress Indicator
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(5.dp))
                        .background(
                            Brush.horizontalGradient(listOf(JoyLightSecondary, SuccessGreen))
                        )
                )
            }
            Text(
                text = "${(progress * 100).toInt()}% Journey Completed",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // Questions List
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(questions, key = { _, item -> item.id }) { index, question ->
                ModernQuestionCard(
                    question = question,
                    answer = answers[question.id] ?: 5,
                    onAnswerChange = { onAnswerChange(question.id, it) },
                    index = index
                )
            }
        }

        // Navigation Controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (pageIndex > 0) {
                FilledIconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(64.dp)
                        .shadow(4.dp, RoundedCornerShape(20.dp)),
                    shape = RoundedCornerShape(20.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.size(28.dp))
                }
            }

            Button(
                onClick = onNext,
                modifier = Modifier
                    .height(64.dp)
                    .weight(1f)
                    .shadow(8.dp, RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = JoyLightPrimary),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Text(
                    text = if (!isLastPage) "Next Adventure! 🚀" else "Show My Magic! 🌈",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                if (!isLastPage) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next")
                }
            }
        }
    }
}

@Composable
fun ModernQuestionCard(
    question: Question,
    answer: Int,
    onAnswerChange: (Int) -> Unit,
    index: Int
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn(animationSpec = tween(600, delayMillis = index * 100))
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(6.dp, RoundedCornerShape(32.dp)),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = question.text,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, lineHeight = 26.sp),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = "$answer",
                    style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Black),
                    color = MagicPurple
                )

                Slider(
                    value = answer.toFloat(),
                    onValueChange = { onAnswerChange(it.toInt()) },
                    valueRange = 1f..10f,
                    steps = 8,
                    colors = SliderDefaults.colors(
                        thumbColor = EnergyOrange,
                        activeTrackColor = EnergyOrange,
                        inactiveTrackColor = EnergyOrange.copy(alpha = 0.2f)
                    ),
                    modifier = Modifier.padding(vertical = 12.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Nope 🙊", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = ErrorRed)
                    Text("YES! 🦁", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = SuccessGreen)
                }
            }
        }
    }
}
