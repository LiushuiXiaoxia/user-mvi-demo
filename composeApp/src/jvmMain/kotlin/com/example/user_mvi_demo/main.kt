package com.example.user_mvi_demo

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() {
    initKmpApp()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            alwaysOnTop = true,
            title = "Usermvidemo",
        ) {
            App()
        }
    }
}