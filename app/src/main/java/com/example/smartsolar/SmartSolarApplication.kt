package com.example.smartsolar

import android.app.Application
import android.content.Intent

class SmartSolarApplication : Application() {
    lateinit var dataManager: DataManager

    override fun onCreate() {
        super.onCreate()
        android.util.Log.d("SmartSolarApp", "Application onCreate started")

        dataManager = DataManager.getInstance(this)
        android.util.Log.d("SmartSolarApp", "DataManager created")

        dataManager.startService()
        android.util.Log.d("SmartSolarApp", "DataManager service started")

        // Temporarily disable network test service to focus on MQTT
        // val networkTestIntent = Intent(this, NetworkTestService::class.java)
        // startService(networkTestIntent)
    }

    override fun onTerminate() {
        super.onTerminate()
        dataManager.stopService()
    }
} 