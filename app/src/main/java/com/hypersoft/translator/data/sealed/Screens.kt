package com.hypersoft.translator.data.sealed

sealed class Screens(val route : String) {
    object Home : Screens("home_screen")
    object History : Screens("search_screen")
    object Settings : Screens("profile_screen")
}