package com.example.test6remastered.app

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    companion object {
        lateinit var appContext: Application
    }

}