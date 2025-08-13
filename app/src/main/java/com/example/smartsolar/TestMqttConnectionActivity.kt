package com.example.smartsolar

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class TestMqttConnectionActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "TestMqttConnection"
    }

    private lateinit var statusText: TextView
    private lateinit var connectButton: Button
    private val testScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_mqtt_connection)
        
        statusText = findViewById(R.id.statusText)
        connectButton = findViewById(R.id.connectButton)
        
        connectButton.setOnClickListener {
            testMqttConnection()
        }
        
        Log.d(TAG, "TestMqttConnectionActivity created")
    }

    private fun testMqttConnection() {
        statusText.text = "Testing connection..."
        Log.d(TAG, "Starting MQTT connection test...")
        
        testScope.launch {
            try {
                withTimeout(30000) { // 30 second timeout for entire test
                    // Test 0: Basic object creation (no network)
                    testBasicObjectCreation()
                    
                    // Test 1: Network object creation
                    testNetworkObjectCreation()
                    
                    // Test 2: DNS resolution test
                    testDnsResolution()
                    
                    // Test 3: Simple HTTP connection with very short timeout
                    testSimpleHttpConnection()
                    
                    // Test 4: Alternative HTTP connection
                    testAlternativeHttpConnection()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Test failed: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    statusText.text = "Test failed: ${e.message}"
                }
            }
        }
    }

    private suspend fun testBasicObjectCreation() {
        withContext(Dispatchers.Main) {
            statusText.text = "Test 0: Basic objects..."
        }
        
        Log.d(TAG, "Test 0: Testing basic object creation")
        
        try {
            // Test basic object creation without network
            val testString = "Hello World"
            val testNumber = 42
            val testList = listOf(1, 2, 3, 4, 5)
            
            Log.d(TAG, "✓ Basic object creation successful: $testString, $testNumber, $testList")
            withContext(Dispatchers.Main) {
                statusText.text = "✓ Test 0: Basic objects OK"
            }
        } catch (e: Exception) {
            Log.e(TAG, "✗ Test 0 failed: ${e.message}", e)
            withContext(Dispatchers.Main) {
                statusText.text = "✗ Test 0 failed: ${e.message}"
            }
        }
    }

    private suspend fun testNetworkObjectCreation() {
        withContext(Dispatchers.Main) {
            statusText.text = "Test 1: Network objects..."
        }
        
        Log.d(TAG, "Test 1: Testing network object creation")
        
        try {
            withContext(Dispatchers.IO) {
                // Just create network objects without connecting
                val url = java.net.URL("http://example.com")
                val socket = java.net.Socket()
                val inetAddress = java.net.InetAddress.getByName("localhost")
                
                Log.d(TAG, "✓ Network object creation successful: $url, $socket, $inetAddress")
                withContext(Dispatchers.Main) {
                    statusText.text = "✓ Test 1: Network objects OK"
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "✗ Test 1 failed: ${e.message}", e)
            withContext(Dispatchers.Main) {
                statusText.text = "✗ Test 1 failed: ${e.message}"
            }
        }
    }

    private suspend fun testDnsResolution() {
        withContext(Dispatchers.Main) {
            statusText.text = "Test 2: DNS resolution..."
        }
        
        Log.d(TAG, "Test 2: Testing DNS resolution")
        
        try {
            withContext(Dispatchers.IO) {
                Log.d(TAG, "Attempting to resolve httpbin.org...")
                val address = java.net.InetAddress.getByName("httpbin.org")
                Log.d(TAG, "✓ DNS resolution successful: ${address.hostAddress}")
                
                Log.d(TAG, "Attempting to resolve google.com...")
                val googleAddress = java.net.InetAddress.getByName("google.com")
                Log.d(TAG, "✓ Google DNS resolution successful: ${googleAddress.hostAddress}")
                
                withContext(Dispatchers.Main) {
                    statusText.text = "✓ Test 2: DNS OK"
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "✗ Test 2 failed: ${e.message}", e)
            withContext(Dispatchers.Main) {
                statusText.text = "✗ Test 2 failed: ${e.message}"
            }
        }
    }

    private suspend fun testSimpleHttpConnection() {
        withContext(Dispatchers.Main) {
            statusText.text = "Test 3: Simple HTTP..."
        }
        
        Log.d(TAG, "Test 3: Testing simple HTTP connection")
        
        try {
            withContext(Dispatchers.IO) {
                // Try a very simple HTTP connection with very short timeout
                Log.d(TAG, "Creating URL object...")
                val url = java.net.URL("http://httpbin.org/get")
                Log.d(TAG, "Creating HttpURLConnection...")
                val connection = url.openConnection() as java.net.HttpURLConnection
                Log.d(TAG, "Setting timeouts...")
                connection.connectTimeout = 2000  // 2 second timeout
                connection.readTimeout = 2000
                connection.requestMethod = "HEAD"  // Just HEAD request, no body
                
                Log.d(TAG, "Attempting HTTP HEAD request to httpbin.org...")
                val responseCode = connection.responseCode
                Log.d(TAG, "Got response code: $responseCode")
                connection.disconnect()
                
                Log.d(TAG, "✓ HTTP connection successful: $responseCode")
                withContext(Dispatchers.Main) {
                    statusText.text = "✓ Test 3: HTTP OK ($responseCode)"
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "✗ Test 3 failed: ${e.message}", e)
            Log.e(TAG, "Exception type: ${e.javaClass.simpleName}")
            withContext(Dispatchers.Main) {
                statusText.text = "✗ Test 3 failed: ${e.message}"
            }
        }
    }

    private suspend fun testAlternativeHttpConnection() {
        withContext(Dispatchers.Main) {
            statusText.text = "Test 4: Alternative HTTP..."
        }
        
        Log.d(TAG, "Test 4: Testing alternative HTTP connection")
        
        try {
            withContext(Dispatchers.IO) {
                // Try a different approach with Google
                Log.d(TAG, "Attempting HTTP connection to google.com...")
                val url = java.net.URL("http://www.google.com")
                val connection = url.openConnection() as java.net.HttpURLConnection
                connection.connectTimeout = 3000  // 3 second timeout
                connection.readTimeout = 3000
                connection.requestMethod = "HEAD"
                
                val responseCode = connection.responseCode
                Log.d(TAG, "Google response code: $responseCode")
                connection.disconnect()
                
                Log.d(TAG, "✓ Alternative HTTP connection successful: $responseCode")
                withContext(Dispatchers.Main) {
                    statusText.text = "✓ Test 4: Alternative HTTP OK ($responseCode)"
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "✗ Test 4 failed: ${e.message}", e)
            Log.e(TAG, "Exception type: ${e.javaClass.simpleName}")
            withContext(Dispatchers.Main) {
                statusText.text = "✗ Test 4 failed: ${e.message}"
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        testScope.cancel()
    }
}
