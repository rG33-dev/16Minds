package com.example.whu.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whu.model.Question
import com.example.whu.ui.theme.*

@Composable
fun QuizScreen(
    questions: List<Question>,
    pageIndex: Int,
    totalQuestions: Int,
    answers: Map<Int, Int>,
    isLastPage: Boolean,
    onAnswerChange: (Int, Int) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    val progress = ((pageIndex + 1) * 5).coerceAtMost(totalQuestions).toFloat() / totalQuestions

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .clip(RoundedCornerShape(8.dp)),
            color =GrassGreen,
            trackColor = GrassGreen.copy(alpha = 0.2f)
        )
        
        Text(
            text = "Page ${pageIndex + 1}",
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = GrassGreen
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(questions) { question ->
                QuestionItem(
                    question = question,
                    answer = answers[question.id] ?: 5,
                    onAnswerChange = { onAnswerChange(question.id, it) }
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FilledIconButton(
                onClick = onBack,
                modifier = Modifier.size(64.dp),
                shape = RoundedCornerShape(16.dp),
                colors = IconButtonDefaults.filledIconButtonColors(containerColor = SkyBlue)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.size(32.dp))
            }

            Button(
                onClick = onNext,
                modifier = Modifier
                    .height(64.dp)
                    .weight(1f)
                    .padding(start = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SkyBlue)
            ) {
                Text(
                    text = if (!isLastPage) "Next Page" else "Show My Magic! ✨",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
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
fun QuestionItem(
    question: Question,
    answer: Int,
    onAnswerChange: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = question.text,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = BrightPurple
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Score: $answer",
                style = MaterialTheme.typography.titleLarge,
                color = SkyBlue,
                fontWeight = FontWeight.Black
            )
            
            Slider(
                value = answer.toFloat(),
                onValueChange = { onAnswerChange(it.toInt()) },
                valueRange = 1f..10f,
                steps = 8,
                colors = SliderDefaults.colors(
                    thumbColor = SoftOrange,
                    activeTrackColor = SoftOrange,
                    inactiveTrackColor = SoftOrange.copy(alpha = 0.24f)
                )
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("No way!", fontSize = 10.sp, color = CandyRed)
                Text("Totally!", fontSize = 10.sp, color = GrassGreen)
            }
        }
    }
}
