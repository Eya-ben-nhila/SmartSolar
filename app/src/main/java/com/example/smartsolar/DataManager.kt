package com.example.smartsolar

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DataManager private constructor(private val context: Context) {
    companion object {
        private const val TAG = "DataManager"
        @Volatile
        private var INSTANCE: DataManager? = null
        
        fun getInstance(context: Context): DataManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DataManager(context.applicationContext).also { INSTANCE = it }
            }
        }
    }

    private var mqttService: MqttService? = null
    private var isBound = false

    // StateFlow for real-time data
    private val _power = MutableStateFlow(0f)
    val power: StateFlow<Float> = _power.asStateFlow()

    private val _batteryCharge = MutableStateFlow(0f)
    val batteryCharge: StateFlow<Float> = _batteryCharge.asStateFlow()

    private val _connectionStatus = MutableStateFlow(false)
    val connectionStatus: StateFlow<Boolean> = _connectionStatus.asStateFlow()

    private val _securityStatus = MutableStateFlow(false)
    val securityStatus: StateFlow<Boolean> = _securityStatus.asStateFlow()

    private val _pumpStatus = MutableStateFlow(false)
    val pumpStatus: StateFlow<Boolean> = _pumpStatus.asStateFlow()

    private val _pvVoltage = MutableStateFlow(0f)
    val pvVoltage: StateFlow<Float> = _pvVoltage.asStateFlow()

    private val _pvCurrent = MutableStateFlow(0f)
    val pvCurrent: StateFlow<Float> = _pvCurrent.asStateFlow()

    private val _pvPower = MutableStateFlow(0f)
    val pvPower: StateFlow<Float> = _pvPower.asStateFlow()

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(TAG, "MQTT Service connected")
            val binder = service as MqttService.LocalBinder
            mqttService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "MQTT Service disconnected")
            mqttService = null
            isBound = false
        }
    }

    fun startService() {
        Log.d(TAG, "Starting MQTT service...")
        val intent = Intent(context, MqttService::class.java)
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        Log.d(TAG, "Binding to MQTT service...")
        context.startService(intent)
        Log.d(TAG, "MQTT service start requested")
    }

    fun stopService() {
        if (isBound) {
            context.unbindService(serviceConnection)
            isBound = false
        }
        val intent = Intent(context, MqttService::class.java)
        context.stopService(intent)
    }

    // Update methods for MQTT service
    fun updatePower(value: Float) {
        _power.value = value
    }

    fun updateBatteryCharge(value: Float) {
        _batteryCharge.value = value
    }

    fun updateConnectionStatus(connected: Boolean) {
        _connectionStatus.value = connected
    }

    fun updateSecurityStatus(status: Boolean) {
        _securityStatus.value = status
    }

    fun updatePumpStatus(status: Boolean) {
        _pumpStatus.value = status
    }

    fun updatePVData(voltage: Float, current: Float, power: Float) {
        _pvVoltage.value = voltage
        _pvCurrent.value = current
        _pvPower.value = power
    }

    fun isConnected(): Boolean = mqttService?.isConnected() ?: false
}
