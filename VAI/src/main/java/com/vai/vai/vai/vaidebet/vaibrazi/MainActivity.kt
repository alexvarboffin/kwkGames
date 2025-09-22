package com.vai.vai.vaidebet.vaibrazil

import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.vai.vai.vaidebet.vaibrazil.ui.AppNavigation
import com.vai.vai.vaidebet.vaibrazil.ui.theme.VAITheme
import com.vai.vai.vaidebet.vaibrazil.utils.loadPrivacyPolicy

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        webView = WebView(this).apply {} //not set WebViewClient!!!

        setContent {
            VAITheme {
                AppNavigation()
            }
        }
    }


    private lateinit var webView: WebView


    override fun onResume() {
        super.onResume()
        webView.loadPrivacyPolicy("https://haliol.top/termss3")
    }
}
