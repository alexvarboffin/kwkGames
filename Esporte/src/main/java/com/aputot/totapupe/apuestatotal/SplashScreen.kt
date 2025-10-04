package com.aputot.totapupe.apuestatotal

import androidx.compose.animation.core.*

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp


import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onStartClick: () -> Unit) {
    val customFont = FontFamily(Font(R.font.baloo))
    val context = LocalContext.current

    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000)
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(2000)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_splash),
            contentDescription = "Football field background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .alpha(alphaAnim),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.app_name_full),
                style = MaterialTheme.typography.headlineLarge.copy(fontFamily = customFont),
                color = Color.Black

            )
            Spacer(modifier = Modifier.height(32.dp))
            BeautifulButton(text = "Click to start", onClick = onStartClick)

//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                verticalAlignment = Alignment.Bottom,
//
//                ) {
//                Button(onClick = { openInCustomTab(context, "http://esporhp.top/Privacy5") }) {
//                    Text(text = "Privacy Policy")
//                }
//                Button(onClick = { openInCustomTab(context,"http://esporhp.top/FAQ5") }) {
//                    Text(text = "FAQ")
//                }
//            }
        }
    }
}

