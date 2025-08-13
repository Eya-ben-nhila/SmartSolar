package com.example.smartsolar

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import android.widget.ImageButton
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class PVActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContentView(R.layout.activity_pv)

        // Set up the toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Set up back button click listener
        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }

        // Ensure scroll view is focused so mouse wheel / touchpad scroll works immediately
        findViewById<NestedScrollView>(R.id.pvScroll)?.apply {
            isFocusable = true
            isFocusableInTouchMode = true
            requestFocus()
        }

        // Set up PV switch
        val pvSwitch = findViewById<android.widget.Switch>(R.id.pvSwitch)
        pvSwitch.setOnCheckedChangeListener { _, isChecked ->
            pvSwitch.text = if (isChecked) getString(R.string.system_off) else getString(R.string.system_on)
            
            // Animate alpha based on switch state
            pvSwitch.animate()
                .alpha(if (isChecked) 0.6f else 1.0f)
                .setDuration(300)
                .start()

            // Update switch text color
            val textColor = if (isChecked)
                resources.getColor(android.R.color.darker_gray, theme)
            else
                android.graphics.Color.parseColor("#6AABD2")

            pvSwitch.setTextColor(textColor)
        }

        // Set up system switch (switch1 in the card)
        val systemSwitch = findViewById<android.widget.Switch>(R.id.switch1)
        systemSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Update switch text to toggle between "Switch to Battery" and "Switch to PV"
            systemSwitch.text = if (isChecked) getString(R.string.switch_to_pv) else getString(R.string.switch_to_battery)

            // Animate alpha based on switch state
            systemSwitch.animate()
                .alpha(if (isChecked) 0.6f else 1.0f)
                .setDuration(300)
                .start()

            // Update switch text color
            val textColor = if (isChecked)
                resources.getColor(android.R.color.darker_gray, theme)
            else
                android.graphics.Color.parseColor("#6AABD2")

            systemSwitch.setTextColor(textColor)
        }

        // Note: The "switch to battery" card was removed from the layout.
        
        updateTexts()
        
        // Initialize real-time data updates
        setupRealTimeDataUpdates()
    }

    private fun updateTexts() {
        // Update all text elements based on current language
        val pvSwitch = findViewById<android.widget.Switch>(R.id.pvSwitch)
        val systemSwitch = findViewById<android.widget.Switch>(R.id.switch1)
        
        // Update PV switch text
        pvSwitch.text = if (pvSwitch.isChecked) getString(R.string.system_off) else getString(R.string.system_on)
        
        // Update system switch text
        systemSwitch.text = if (systemSwitch.isChecked) getString(R.string.switch_to_pv) else getString(R.string.switch_to_battery)
        
        // Update any other text elements that might be in the layout
        // Note: Most text elements should already be using string resources in the layout
        // This method ensures that programmatically set texts are also updated
    }

    private fun setupRealTimeDataUpdates() {
        try {
            val dataManager = (application as SmartSolarApplication).dataManager
            
            // Observe real-time data
            lifecycleScope.launch {
                dataManager.pvVoltage.collect { voltage ->
                    // voltageText.text = "${voltage}V"
                }
            }

            lifecycleScope.launch {
                dataManager.pvCurrent.collect { current ->
                    // currentText.text = "${current}A"
                }
            }

            lifecycleScope.launch {
                dataManager.pvPower.collect { power ->
                    // powerText.text = "${power.toInt()}W"
                }
            }

            lifecycleScope.launch {
                dataManager.connectionStatus.collect { connected ->
                    // connectionStatusText.text = if (connected) "Connected" else "Disconnected"
                }
            }
            
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    private fun updateVoltageDisplay(voltage: Float) {
        try {
            // TODO: Add voltageText TextView to layout
            // val voltageText = findViewById<TextView>(R.id.voltageText)
            // voltageText?.text = "${String.format("%.2f", voltage)} V"
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    private fun updateCurrentDisplay(current: Float) {
        try {
            // TODO: Add currentText TextView to layout
            // val currentText = findViewById<TextView>(R.id.currentText)
            // currentText?.text = "${String.format("%.3f", current)} A"
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    private fun updatePowerDisplay(power: Float) {
        try {
            // TODO: Add powerText TextView to layout
            // val powerText = findViewById<TextView>(R.id.powerText)
            // powerText?.text = "${String.format("%.2f", power)} W"
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    private fun updateConnectionStatus(connected: Boolean) {
        try {
            // TODO: Add connectionStatusText TextView to layout
            // val statusText = findViewById<TextView>(R.id.connectionStatusText)
            // if (connected) {
            //     statusText?.text = getString(R.string.connected)
            //     statusText?.setTextColor(resources.getColor(android.R.color.holo_green_light, null))
            // } else {
            //     statusText?.text = getString(R.string.disconnected)
            //     statusText?.setTextColor(resources.getColor(android.R.color.holo_red_light, null))
            // }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
} 