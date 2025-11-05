package com.example.user_mvi_demo

import com.example.user_mvi_demo.user.UserRepository
import com.example.user_mvi_demo.user.UserViewModel
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

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

//        startKoin {
//            // allowOverride(false)
//            module {
//
//            }
//        }
    }
}

val platformModule: Module
    get() = module {
        single<PlatformContext> { PlatformApp.context }
        single<UserRepository> { UserRepository() }
        viewModelOf(::UserViewModel)
    }