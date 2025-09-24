package com.vai.vai.vai.vaidebet.vaibrazi.ui

import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri

//private fun openUrl(context: Context, url: String) {
//    val builder = CustomTabsIntent.Builder()
//    val customTabsIntent = builder.build()
//    customTabsIntent.launchUrl(context, url.toUri())
//}


fun openUrl(context: Context, url: CharArray) {
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
   val x = String(url)
    println("@@@@ $x")
    customTabsIntent.launchUrl(context, x.toUri())
}