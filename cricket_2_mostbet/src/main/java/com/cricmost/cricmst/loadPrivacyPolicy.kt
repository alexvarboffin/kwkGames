package com.cricmost.cricmst

import android.webkit.WebView

fun WebView.loadPrivacyPolicy(string: String) {
    loadUrl(string)
}