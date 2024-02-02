package com.hypersoft.translator.data

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector

/* drawer menu */
data class DrawerMenu(val icon: ImageVector, val title: String, val route: String)

/* first lang model */
data class FirstLang(val title: String, val langCode: String)

/* second lang model */
data class SecondLang(val icon: ImageVector, val title: String, val langCode: String)

/* text to speech */
data class MainScreenState(val isButtonEnabled:Boolean = true)

/* Settings screen */
data class SettingsData(val icon: ImageVector, val title: String)

/* speech to text */
data class VoiceToTextParserState(
    val isSpeaking: Boolean = false,
    val spokenText: String = "",
    val error: String? = null
)