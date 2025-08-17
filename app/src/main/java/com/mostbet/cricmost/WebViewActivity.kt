package com.mostbet.cricmost

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.mostbet.cricmost.ui.theme.CricketTheme

class WebViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CricketTheme {
                WebViewScreen(url = "https://jojoapp.site/apps")
            }
        }
    }
}

@Composable
fun WebViewScreen(url: String) {
    AndroidView(factory = {
        WebView(it).apply {
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    })
}
