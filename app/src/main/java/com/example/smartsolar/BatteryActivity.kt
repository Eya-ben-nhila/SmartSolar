package com.example.smartsolar

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import android.widget.ImageButton
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class BatteryActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContentView(R.layout.activity_battery)

        // Set up the toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Set up back button click listener
        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }

        // Set up Battery switch
        val batterySwitch = findViewById<android.widget.Switch>(R.id.batterySwitch)
        batterySwitch.setOnCheckedChangeListener { _, isChecked ->
            batterySwitch.text = if (isChecked) getString(R.string.system_off) else getString(R.string.system_on)
            
            // Animate alpha based on switch state
            batterySwitch.animate()
                .alpha(if (isChecked) 0.6f else 1.0f)
                .setDuration(300)
                .start()

            // Update switch text color
            val textColor = if (isChecked)
                resources.getColor(android.R.color.darker_gray, theme)
            else
                android.graphics.Color.parseColor("#6AABD2")

            batterySwitch.setTextColor(textColor)
        }

        // Set up system switch (switch1 in the card)
        val systemSwitch = findViewById<android.widget.Switch>(R.id.switch1)
        systemSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Update switch text to toggle between "Switch to PV" and "Switch to Battery"
            systemSwitch.text = if (isChecked) getString(R.string.switch_to_battery) else getString(R.string.switch_to_pv)
            
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
            
            // Find the switch to PV text view and update it
            val switchToPvCard = findViewById<androidx.cardview.widget.CardView>(R.id.switchToPvCard)
            val textView = switchToPvCard?.findViewById<android.widget.TextView>(R.id.switchToPvText)
            
            if (isChecked) {
                // Switch is ON - change text to "Switch to Battery" with lower opacity
                textView?.text = getString(R.string.switch_to_battery)
                textView?.alpha = 0.5f
            } else {
                // Switch is OFF - change text back to "Switch to PV" with full opacity
                textView?.text = getString(R.string.switch_to_pv)
                textView?.alpha = 1.0f
            }
        }

        // Set up switch to PV button
        val switchToPvCard = findViewById<androidx.cardview.widget.CardView>(R.id.switchToPvCard)
        switchToPvCard?.setOnClickListener {
            val intent = Intent(this, PVActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        
        updateTexts()
        
        // Initialize real-time data updates
        setupRealTimeDataUpdates()
    }

    private fun updateTexts() {
        // Update all text elements based on current language
        val batterySwitch = findViewById<android.widget.Switch>(R.id.batterySwitch)
        val systemSwitch = findViewById<android.widget.Switch>(R.id.switch1)
        val switchToPvCard = findViewById<androidx.cardview.widget.CardView>(R.id.switchToPvCard)
        val textView = switchToPvCard?.findViewById<android.widget.TextView>(R.id.switchToPvText)
        
        // Update battery switch text
        batterySwitch.text = if (batterySwitch.isChecked) getString(R.string.system_off) else getString(R.string.system_on)
        
        // Update system switch text
        systemSwitch.text = if (systemSwitch.isChecked) getString(R.string.switch_to_battery) else getString(R.string.switch_to_pv)
        
        // Update switch to PV text
        if (systemSwitch.isChecked) {
            textView?.text = getString(R.string.switch_to_battery)
        } else {
            textView?.text = getString(R.string.switch_to_pv)
        }
        
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
                    // batteryVoltageText.text = "${voltage}V"
                }
            }

            lifecycleScope.launch {
                dataManager.batteryCharge.collect { battery ->
                    // batteryChargeText.text = "${battery.toInt()}%"
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
    
    private fun updateBatteryVoltageDisplay(voltage: Float) {
        try {
            // TODO: Add batteryVoltageText TextView to layout
            // val voltageText = findViewById<TextView>(R.id.batteryVoltageText)
            // voltageText?.text = "${String.format("%.2f", voltage)} V"
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    private fun updateBatteryChargeDisplay(charge: Int) {
        try {
            // TODO: Add batteryChargeText and batteryLevelText TextViews to layout
            // val chargeText = findViewById<TextView>(R.id.batteryChargeText)
            // chargeText?.text = "$charge%"
            // val batteryLevelText = findViewById<TextView>(R.id.batteryLevelText)
            // batteryLevelText?.text = getBatteryLevelDescription(charge)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    private fun getBatteryLevelDescription(charge: Int): String {
        return when {
            charge >= 80 -> getString(R.string.battery_level_high)
            charge >= 50 -> getString(R.string.battery_level_medium)
            charge >= 20 -> getString(R.string.battery_level_low)
            else -> getString(R.string.battery_level_critical)
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