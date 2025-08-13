package com.example.smartsolar

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*
import java.io.IOException
import java.net.Socket
import java.net.InetSocketAddress

class NetworkTestService : Service() {
    
    companion object {
        private const val TAG = "NetworkTestService"
    }
    
    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    
    // Binder for activity binding
    inner class LocalBinder : Binder() {
        fun getService(): NetworkTestService = this@NetworkTestService
    }
    
    private val binder = LocalBinder()
    
    override fun onBind(intent: Intent): IBinder {
        return binder
    }
    
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "NetworkTestService created")
        testNetworkConnectivity()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
    
    private fun testNetworkConnectivity() {
        serviceScope.launch {
            try {
                Log.d(TAG, "Testing network connectivity...")
                
                // Test basic internet connectivity
                testBasicConnectivity()
                
                // Test MQTT broker connectivity
                testMqttBrokerConnectivity()
                
            } catch (e: Exception) {
                Log.e(TAG, "Network test failed: ${e.message}", e)
            }
        }
    }
    
    private suspend fun testBasicConnectivity() {
        withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "Testing basic internet connectivity...")
                
                // Try using URL connection instead of raw socket
                val url = java.net.URL("https://www.google.com")
                val connection = url.openConnection() as java.net.HttpURLConnection
                connection.connectTimeout = 5000
                connection.readTimeout = 5000
                connection.requestMethod = "HEAD"
                
                val responseCode = connection.responseCode
                connection.disconnect()
                
                if (responseCode == 200) {
                    Log.d(TAG, "✓ Basic internet connectivity: SUCCESS (HTTP)")
                } else {
                    Log.d(TAG, "✓ Basic internet connectivity: SUCCESS (HTTP ${responseCode})")
                }
                
            } catch (e: Exception) {
                Log.e(TAG, "✗ Basic internet connectivity: FAILED - ${e.message}")
                Log.e(TAG, "Exception type: ${e.javaClass.simpleName}")
            }
        }
    }
    
    private suspend fun testMqttBrokerConnectivity() {
        withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "Testing MQTT broker connectivity...")
                
                // Try using URL connection to test if we can reach the broker domain
                val url = java.net.URL("http://broker.hivemq.com")
                val connection = url.openConnection() as java.net.HttpURLConnection
                connection.connectTimeout = 5000
                connection.readTimeout = 5000
                connection.requestMethod = "HEAD"
                
                val responseCode = connection.responseCode
                connection.disconnect()
                
                if (responseCode in 200..399) {
                    Log.d(TAG, "✓ MQTT broker domain reachable: SUCCESS (HTTP ${responseCode})")
                } else {
                    Log.d(TAG, "? MQTT broker domain response: HTTP ${responseCode}")
                }
                
            } catch (e: Exception) {
                Log.e(TAG, "✗ MQTT broker connectivity: FAILED - ${e.message}")
                Log.e(TAG, "Exception type: ${e.javaClass.simpleName}")
            }
        }
    }
}
