package com.myapplication

import MainView
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import di.initKoinIos

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        initKoinIos()
        super.onCreate(savedInstanceState)

        setContent {
            MainView()
        }
    }
}