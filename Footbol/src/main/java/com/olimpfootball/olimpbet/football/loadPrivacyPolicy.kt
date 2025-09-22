package com.olimpfootball.olimpbet.football

import android.webkit.WebView

fun WebView.loadPrivacyPolicy(string: String) {
    loadUrl(string)
}

fun WebView.loadPrivacyPolicy(string: CharArray) {
    loadUrl(String(string))
}