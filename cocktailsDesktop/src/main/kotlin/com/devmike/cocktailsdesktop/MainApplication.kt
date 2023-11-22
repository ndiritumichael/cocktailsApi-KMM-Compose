package com.devmike.cocktailsdesktop

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.devmike.cocktailsdesktop.windows.MainWindow
import di.initKoin
import org.koin.core.Koin

lateinit var koin: Koin

fun main() {
    koin = initKoin(enableNetworkLogs = true).koin

    return application {
        Window(onCloseRequest = ::exitApplication) {
            MainWindow(applicationScope = this@application)
        }
    }
}
