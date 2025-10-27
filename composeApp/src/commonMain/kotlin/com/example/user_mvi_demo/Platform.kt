package com.example.user_mvi_demo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform