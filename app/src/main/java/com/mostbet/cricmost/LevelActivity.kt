package com.mostbet.cricmost

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mostbet.cricmost.ui.theme.CricketTheme
import android.content.Intent
import androidx.compose.ui.platform.LocalContext

class LevelActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CricketTheme {
                LevelScreen()
            }
        }
    }
}

@Composable
fun LevelScreen() {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF004d00)) // Different shade of green
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.padding(16.dp)
        ) {
            items(30) { level ->
                Button(onClick = { context.startActivity(Intent(context, GameActivity::class.java)) }) {
                    Text(text = (level + 1).toString())
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LevelScreenPreview() {
    CricketTheme {
        LevelScreen()
    }
}
