package com.example.whu.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whu.ui.theme.*

@Composable
fun WelcomeScreen(onContinue: () -> Unit) {
    var startAnimation by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        startAnimation = true
    }

    val infiniteTransition = rememberInfiniteTransition(label = "bounce")
    val bounceScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bounce"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            visible = startAnimation,
            enter = fadeIn(tween(1000)) + expandVertically(tween(1000))
        ) {
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .scale(bounceScale)
                    .clip(CircleShape)
                    .background(Brush.linearGradient(RainbowGradient)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "🌈", fontSize = 80.sp)
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Welcome to\nMagic Discovery!",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Black,
                lineHeight = 44.sp,
                letterSpacing = 1.sp
            ),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Ready to find your inner superpower?\nA fun journey awaits you!",
            style = MaterialTheme.typography.bodyLarge.copy(
                lineHeight = 28.sp,
                fontWeight = FontWeight.Medium
            ),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(64.dp))

        Button(
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .shadow(16.dp, RoundedCornerShape(24.dp)),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = JoyLightPrimary,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "START ADVENTURE 🚀",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}
