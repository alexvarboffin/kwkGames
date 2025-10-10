package com.esporte.olimp.yay.splash

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.esporte.olimp.yay.R

import com.esporte.olimp.yay.Blue // Предполагается, что это фирменный голубой цвет


@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun SplashScreen(onStartClick: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "title_animation")
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -20f, // Смещение вверх
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "offset_y"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background( Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF0F2027), // тёмный глубокий
                    Color(0xFF203A43), // холодный сине-серый
                    Color(0xFF2C5364)  // мягкий аквамариновый
                )
            )), // Темный фон
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Анимированный текст "WIN HORSE"
            Text(
                text = stringResource(R.string.app_name_full),
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Blue, // Фирменный голубой цвет
                modifier = Modifier.offset(y = offsetY.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Изображение лошади (пока без ColorFilter, можно добавить позже)
            Image(
                painter = painterResource(id = R.drawable.horse_img), // Предполагается, что horse_img.webp будет скопирован в drawable
                contentDescription = "Horse Image",
                modifier = Modifier.size(250.dp)
            )

            Spacer(modifier = Modifier.height(64.dp))

            // Кнопка "click to start"
            PrettyButton(onStartClick)
        }
    }
}

@Composable
private fun PrettyButton(onStartClick: () -> Unit) {
    Button(
        onClick = onStartClick,
        modifier = Modifier.padding(horizontal = 32.dp)
    ) {
        Text("CLICK TO START", fontSize = 20.sp)
    }
}