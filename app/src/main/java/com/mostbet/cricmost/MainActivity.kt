package com.mostbet.cricmost

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mostbet.cricmost.ui.theme.CricketTheme

import android.content.Intent
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CricketTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF006400)) // Dark Green background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("CRICKET FROUA", fontSize = 32.sp, color = Color.White)
            Spacer(modifier = Modifier.height(150.dp))
            Button(onClick = { context.startActivity(Intent(context, LevelActivity::class.java)) },
                  modifier = Modifier.fillMaxWidth(0.8f)) {
                Text("Career Mode")
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { /* TODO: Handle Endless Mode click */ },
                  modifier = Modifier.fillMaxWidth(0.8f)) {
                Text("Endless Mode")
            }
            Spacer(modifier = Modifier.height(40.dp))
            Button(onClick = { context.startActivity(Intent(context, WebViewActivity::class.java)) },
                  modifier = Modifier.fillMaxWidth(0.6f)) {
                Text("Privacy Policy")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CricketTheme {
        MainScreen()
    }
}