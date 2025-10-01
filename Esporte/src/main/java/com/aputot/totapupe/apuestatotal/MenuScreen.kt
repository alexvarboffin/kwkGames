package com.aputot.totapupe.apuestatotal

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.aputot.apuestatotal.apuestape.R


@Composable
fun MenuScreen(onPlayClick: () -> Unit, onAudioClick: () -> Unit, onFullscreenClick: () -> Unit, onInfoClick: () -> Unit) {
    var audioOn by remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg_menu),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = onPlayClick) {
                Text("PLAY")
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopEnd),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = onInfoClick) {
                Icon(painter = painterResource(id = R.drawable.but_info), contentDescription = "Info")
            }
            IconButton(onClick = { 
                audioOn = !audioOn
                onAudioClick()
            }) {
                Icon(painter = painterResource(id = if(audioOn) R.drawable.audio_icon else R.drawable.audio_icon), contentDescription = "Audio")
            }
            IconButton(onClick = onFullscreenClick) {
                Icon(painter = painterResource(id = R.drawable.but_fullscreen), contentDescription = "Fullscreen")
            }
        }
    }
}