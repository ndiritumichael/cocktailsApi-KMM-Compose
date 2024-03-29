package com.devmike

import MainView
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import di.initKoin

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        initKoin(BuildConfig.DEBUG)
        super.onCreate(savedInstanceState)

        setContent {
            MainView()
        }
    }
}
