package com.hypersoft.translator

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.hypersoft.translator.drawer.ComposeDrawer
import com.hypersoft.translator.extentions.speechText
import com.hypersoft.translator.extentions.toasty
import com.hypersoft.translator.ui.theme.TranslatorTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TranslatorTheme {
                ComposeDrawer()
            }
        }
    }
}
