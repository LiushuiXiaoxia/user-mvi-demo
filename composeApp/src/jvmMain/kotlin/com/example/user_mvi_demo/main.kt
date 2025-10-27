package com.example.user_mvi_demo

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Usermvidemo",
    ) {
        App()
    }
}