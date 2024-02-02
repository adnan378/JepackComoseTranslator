package com.hypersoft.translator.viewmodels

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.hypersoft.translator.data.MainScreenState
import com.hypersoft.translator.extentions.toasty
import java.util.Locale

class MainViewModel : ViewModel() {

    private val _state = mutableStateOf(MainScreenState())
    val state: State<MainScreenState> = _state
    private var textToSpeech: TextToSpeech? = null

    fun textToSpeech(context: Context, text: String) {
        if (text.isNotEmpty()) {
            textToSpeech = TextToSpeech(
                context
            ) {
                if (it == TextToSpeech.SUCCESS) {
                    textToSpeech?.let { txtToSpeech ->
                        txtToSpeech.language = Locale.US
                        txtToSpeech.setSpeechRate(1.0f)
                        txtToSpeech.speak(
                            text,
                            TextToSpeech.QUEUE_ADD,
                            null,
                            null
                        )
                    }
                }
                _state.value = state.value.copy(
                    isButtonEnabled = true
                )
            }
        } else {
            context.toasty("write something")
        }
    }
}