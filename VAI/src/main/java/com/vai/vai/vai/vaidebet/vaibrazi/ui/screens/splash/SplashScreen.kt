package com.vai.vai.vaidebet.vaibrazil.ui.screens.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vai.vai.vaidebet.vaibrazil.presentation.screens.splash.SplashViewModel
import com.vai.vai.vaidebet.vaibrazil.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    onNavigateToHome: () -> Unit,
    viewModel: SplashViewModel = koinViewModel(),
) {
    val navigationState by viewModel.navigationState.collectAsState()

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

//            Box(
//                modifier = Modifier
//                    .clip(RoundedCornerShape(16.dp))
//                    .background(MaterialTheme.colorScheme.primary)
//                    .padding(horizontal = 32.dp, vertical = 16.dp)
//            ) {
//                Text(
//                    text = "VAI SWIPE",
//                    fontSize = 48.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = MaterialTheme.colorScheme.onPrimary
//                )
//            }


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
