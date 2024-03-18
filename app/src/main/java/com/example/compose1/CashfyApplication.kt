package com.example.compose1

import android.app.Application

class CashfyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}