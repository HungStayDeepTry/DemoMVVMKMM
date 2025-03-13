package com.example.demomvvmkmm

import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidContext

class MyApplication: Application() {
    companion object {
        private lateinit var appContext: Context

        // Lấy applicationContext từ bất kỳ đâu trong ứng dụng
        fun getAppContext(): Context {
            return appContext
        }
    }
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        initKoin{
            androidContext(this@MyApplication)
        }
    }
}