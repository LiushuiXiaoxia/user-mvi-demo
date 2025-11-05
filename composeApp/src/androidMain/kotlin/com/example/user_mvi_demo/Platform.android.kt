package com.example.user_mvi_demo

import android.app.Application
import android.content.Context
import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

/**
 * 通常在 Application.onCreate() 调用一次
 */
fun initKmpApp(application: Application) {
    PlatformApp.init(AndroidPlatformContext(application.applicationContext))
}

class AndroidPlatformContext(val appContext: Context) : PlatformContext {
}