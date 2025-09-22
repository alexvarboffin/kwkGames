package com.cricmost.cricmst

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun MainScreen(
    onCareerModeClick: () -> Unit,
    onEndlessModeClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    onFaqClick: () -> Unit
) {
    Box(modifier = Modifier.Companion.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_main_background),
            contentDescription = "Background",
            contentScale = ContentScale.Companion.Crop,
            modifier = Modifier.Companion.fillMaxSize()
        )
        Column(
            modifier = Modifier.Companion
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Companion.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.Companion.height(150.dp))
            BeautifulButton(
                onClick = onCareerModeClick,
                text = "Career Mode",
                modifier = Modifier.Companion.fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.Companion.height(20.dp))
            BeautifulButton(
                onClick = onEndlessModeClick,
                text = "Endless Mode",
                modifier = Modifier.Companion.fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.Companion.height(40.dp))
            Row(
                modifier = Modifier.Companion.fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BeautifulButton(
                    onClick = onPrivacyClick,
                    text = "Privacy",
                    modifier = Modifier.Companion.weight(1f)
                )
                Spacer(modifier = Modifier.Companion.width(16.dp))
                BeautifulButton(
                    onClick = onFaqClick,
                    text = "  FAQ  ",
                    modifier = Modifier.Companion.weight(1f)
                )
            }
        }
    }
}