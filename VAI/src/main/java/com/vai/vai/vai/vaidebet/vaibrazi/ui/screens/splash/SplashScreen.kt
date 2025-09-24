package com.vai.vai.vai.vaidebet.vaibrazi.ui.screens.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vai.vai.vai.vaidebet.vaibrazi.presentation.screens.splash.SplashViewModel
import com.vai.vai.vai.vaidebet.vaibrazi.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    onNavigateToHome: () -> Unit,
    viewModel: SplashViewModel = koinViewModel(),
) {
    val navigationState by viewModel.navigationState.collectAsState()
    val customFont = FontFamily(Font(R.font.baloo))

    LaunchedEffect(navigationState) {
        if (navigationState is SplashViewModel.NavigationState.NavigateToHome) {
            onNavigateToHome()
        }
    }

    val infiniteTransition = rememberInfiniteTransition()
    val buttonAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val logoScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_splash_def),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .scale(logoScale)
                    .clip(shape = CircleShape)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_splash),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    //.background(MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 32.dp, vertical = 16.dp)
            ) {
//                Text(
//                    fontFamily = customFont,
//                    text = "VAI SWIPE",
//                    fontSize = 48.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = MaterialTheme.colorScheme.onPrimary
//                )
//                Box(
//                    modifier = Modifier
//                        .clip(RoundedCornerShape(16.dp))
//                        .padding(horizontal = 32.dp, vertical = 16.dp)
//                ) {
//                    // Основной текст (верхний слой)
//                    Text(
//                        fontFamily = customFont,
//                        text = "VAI SWIPE",
//                        fontSize = 48.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = MaterialTheme.colorScheme.onPrimary,
//                        modifier = Modifier.offset(x = (-2).dp, y = (-2).dp)
//                    )
//
//                    // Тень/нижний слой для 3D эффекта
//                    Text(
//                        fontFamily = customFont,
//                        text = "VAI SWIPE",
//                        fontSize = 48.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
//                        modifier = Modifier.offset(x = 2.dp, y = 2.dp)
//                    )
//                }


//                Box(
////                    modifier = Modifier
////                        .clip(RoundedCornerShape(16.dp))
////                        .padding(horizontal = 32.dp, vertical = 16.dp)
////                ) {
////                    val text = "VAI SWIPE"
////                    val colors = listOf(
////                        Color(0xFFE57373), // Красный
////                        Color(0xFF81C784), // Зеленый
////                        Color(0xFF64B5F6), // Синий
////                        Color(0xFFFFB74D), // Оранжевый
////                        Color(0xFFBA68C8), // Фиолетовый
////                        Color(0xFF4DB6AC), // Бирюзовый
////                        Color(0xFFAED581), // Светло-зеленый
////                        Color(0xFF7986CB)  // Индиго
////                    )
////
////                    // Фоновая тень для 3D эффекта
////                    Text(
////                        fontFamily = customFont,
////                        text = text,
////                        fontSize = 48.sp,
////                        fontWeight = FontWeight.Bold,
////                        color = Color.Black.copy(alpha = 0.3f),
////                        modifier = Modifier.offset(x = 3.dp, y = 3.dp)
////                    )
////
////                    // Основной текст с разными цветами
////                    Text(
////                        fontFamily = customFont,
////                        text = buildAnnotatedString {
////                            text.forEachIndexed { index, char ->
////                                withStyle(
////                                    style = SpanStyle(
////                                        color = colors[index % colors.size],
////                                        shadow = Shadow(
////                                            color = Color.Black.copy(alpha = 0.5f),
////                                            offset = Offset(2f, 2f),
////                                            blurRadius = 4f
////                                        )
////                                    )
////                                ) {
////                                    append(char.toString())
////                                }
////                            }
////                        },
////                        fontSize = 48.sp,
////                        fontWeight = FontWeight.Bold
////                    )
////                }


                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .padding(horizontal = 32.dp, vertical = 16.dp)
                ) {
                    val text = "VAI SWIPE"

                    // Многослойный 3D эффект
                    Text( // Самый дальний слой
                        text = text,
                        fontFamily = customFont,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black.copy(alpha = 0.2f),
                        modifier = Modifier.offset(x = 4.dp, y = 4.dp)
                    )

                    Text( // Средний слой
                        text = text,
                        fontFamily = customFont,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black.copy(alpha = 0.4f),
                        modifier = Modifier.offset(x = 2.dp, y = 2.dp)
                    )

                    // Основной текст с градиентом для каждой буквы
                    Text(
                        text = buildAnnotatedString {
                            text.forEachIndexed { index, char ->
                                val colorStops = arrayOf(
                                    0.0f to Color(0xFFD500F9),
                                    0.5f to Color(0xFFF50057),
                                    1.0f to Color(0xFF1DE9B6)
                                )

                                withStyle(
                                    style = SpanStyle(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(
                                                Color.White,
                                                colorStops[index % colorStops.size].second
                                            )
                                        )
                                    )
                                ) {
                                    append(char.toString())
                                }
                            }
                        },
                        fontFamily = customFont,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.3f),
                                offset = Offset(1f, 1f),
                                blurRadius = 2f
                            )
                        )
                    )
                }

            }


            Spacer(modifier = Modifier.height(64.dp))

            Button(
                onClick = { viewModel.onStartClicked() },
                modifier = Modifier.alpha(buttonAlpha)
            ) {
                Text(text = "Click to start", fontSize = 24.sp)
            }
        }
    }
}
