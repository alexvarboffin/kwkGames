package com.vai.vai.vai.vaidebet.vaibrazi

import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.vai.vai.vai.vaidebet.vaibrazi.ui.AppNavigation
import com.vai.vai.vai.vaidebet.vaibrazi.ui.theme.VAITheme

import com.walhalla.sdk.utils.loadPrivacyPolicy

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
        webView.loadPrivacyPolicy(x)
    }
}
