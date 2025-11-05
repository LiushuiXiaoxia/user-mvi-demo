package com.example.user_mvi_demo

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

interface PlatformContext

object PlatformApp {

    private var _context: PlatformContext? = null

    val context: PlatformContext
        get() = _context ?: error("PlatformApp not initialized")

    fun init(platformContext: PlatformContext) {
        this._context = platformContext

        Napier.base(DebugAntilog())
        Napier.i { "PlatformApp.init context = $context" }
    }
}