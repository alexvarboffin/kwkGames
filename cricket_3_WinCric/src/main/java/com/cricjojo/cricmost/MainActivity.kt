package com.cricjojo.cricmost

import android.content.Context
import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.cricjojo.cricmost.ui.theme.CricketTheme
import androidx.core.net.toUri

import com.walhalla.sdk.utils.loadPrivacyPolicy


const val GRID_SIZE = 8
const val ENDLESS_MODE_TIME = 60f
const val PREFS_NAME = "CricketPrefs"
const val KEY_UNLOCKED_LEVEL = "unlockedLevel"

sealed class Screen(val route: String) {
    object MainMenu : Screen("main_menu")
    object LevelSelect : Screen("level_select")
    object GameCareer : Screen("game_career/{level}") {
        fun createRoute(level: Int) = "game_career/$level"
    }
    object GameEndless : Screen("game_endless")
}

class MainActivity : ComponentActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        webView = WebView(this)

        setContent {
            CricketTheme {
                AppNavigation()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        webView.loadPrivacyPolicy("https://rotyik.top/terms6".toCharArray())
    }
}

fun openUrlInCustomTab(context: Context, url: String) {
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    customTabsIntent.launchUrl(context, url.toUri())
}


@Composable
fun LevelItem(level: Int, stars: Int, isLocked: Boolean, onClick: () -> Unit) {



//    Card(
//        modifier = Modifier
//            .size(80.dp)
//            .clickable { navController.navigate("game") },
//        shape = RoundedCornerShape(8.dp)
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.LightGray), // Placeholder for level state (locked/unlocked)
//            contentAlignment = Alignment.Center
//        ) {
//            Text(text = "$level", fontSize = 24.sp, color = Color.Black)
//        }
//    }
    val customFont = FontFamily(Font(R.font.custom_font))
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier//.alpha(if (isLocked) 0.9f else 1f)
    ) {
        Button(
            onClick = onClick,
            enabled = !isLocked,
            //shape = CircleShape,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.size(80.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues(0.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFFAD961),
                                Color(0xFFF76B1C)
                            )
                        )
                    ), contentAlignment = Alignment.Center
            ) {

//                Image(
//                    painter = painterResource(id = R.drawable.ic_target_ball_circle),
//                    contentDescription = null,
//                    modifier = Modifier.fillMaxSize(),
//                    contentScale = ContentScale.Crop
//                )


                if (isLocked) {
                    Text(text = "🔒", fontSize = 32.sp,
                            color = Color.Black,)
                } else {
                    Text(
                        text = level.toString(),
                        fontSize = 32.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold, fontFamily = customFont
                    )
                }
            }
        }
        Row(modifier = Modifier.padding(top = 4.dp)) {
            repeat(3) { index -> Text(text = if (index < stars) "⭐" else "☆", fontSize = 24.sp) }
        }
    }
}

@Composable
fun TimeoutDialog(score: Int, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card {
            Column(
                Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "TIMEOUT!",
                    fontWeight = FontWeight.Bold
                ); Text("SCORE: $score"); Button(onClick = onDismiss) { Text("Main Menu") }
            }
        }
    }
}

