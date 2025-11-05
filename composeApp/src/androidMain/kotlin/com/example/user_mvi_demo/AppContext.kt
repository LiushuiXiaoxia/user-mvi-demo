package com.example.user_mvi_demo

import android.app.Application

class AppContext : Application() {

    override fun onCreate() {
        super.onCreate()

        initKmpApp(this)
    }
}