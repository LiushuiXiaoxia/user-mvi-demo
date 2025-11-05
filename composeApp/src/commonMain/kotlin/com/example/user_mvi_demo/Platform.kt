package com.example.user_mvi_demo

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
    }
}