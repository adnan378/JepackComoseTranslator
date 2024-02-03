package com.hypersoft.translator.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import com.hypersoft.translator.R
import com.hypersoft.translator.data.DrawerMenu
import com.hypersoft.translator.enums.MainRoute

val menus = arrayOf(
    DrawerMenu(Icons.Filled.Face, "Articles", MainRoute.Articles.name),
    DrawerMenu(Icons.Filled.Settings, "Settings", MainRoute.Settings.name),
    DrawerMenu(Icons.Filled.Info, "About Us", MainRoute.About.name)
)

fun Context.isNetworkConnected(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
}

fun Context?.shareText(text: String) {
    this?.let {
        try {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, it.getString(R.string.app_name))
            sendIntent.putExtra(
                Intent.EXTRA_TEXT, text
            )
            sendIntent.type = "text/plain"
            it.startActivity(sendIntent)
        } catch (_: Exception) {
        }
    }
}

fun Activity?.rateUs() {
    this?.let {
        try {
            it.startActivity(
                Intent(
                    "android.intent.action.VIEW",
                    Uri.parse("https://play.google.com/store/apps/details?id=" + it.packageName)
                )
            )
        } catch (_: Exception) {
        }
    }
}

fun Activity?.privacyPolicy() {
    this?.let {
        try {
            it.startActivity(
                Intent(
                    "android.intent.action.VIEW",
                    Uri.parse(it.getString(R.string.privacy_link))
                )
            )
        } catch (_: Exception) {
        }
    }
}

fun Activity?.shareApp() {
    this?.let {
        try {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, it.getString(R.string.app_name))
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "https://play.google.com/store/apps/details?id=${it.packageName}"
            )
            sendIntent.type = "text/plain"
            it.startActivity(sendIntent)
        } catch (_: Exception) {
        }
    }
}

