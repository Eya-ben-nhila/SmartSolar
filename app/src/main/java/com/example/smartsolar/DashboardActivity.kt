package com.example.smartsolar

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.os.LocaleListCompat
import androidx.appcompat.app.AppCompatDelegate
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class DashboardActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        try {
            // Set up bottom navigation
            val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
            bottomNavigation.setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.navigation_charts -> {
                        val intent = Intent(this, ThresholdsActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        true
                    }
                    R.id.navigation_history -> {
                        val intent = Intent(this, HistoryActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        true
                    }
                    R.id.navigation_notifications -> {
                        val intent = Intent(this, NotificationsActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        true
                    }
                    R.id.navigation_control -> {
                        val intent = Intent(this, PVActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        true
                    }
                    R.id.navigation_profile -> {
                        val intent = Intent(this, ProfileActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        true
                    }
                    else -> false
                }
            }

            // Do not highlight any bottom nav tab on the dashboard to avoid confusion

            // Set up card click listeners
            setupCardClickListeners()
            
            // Set up horizontal navigation click listeners
            setupHorizontalNavigationListeners()

            // Set up toolbar menu button to open the Menu screen
            setupToolbarMenuListener()

            // Language toggle button on dashboard
            setupLanguageToggle()

            // Wire the dashboard switch
            setupDashboardSwitch()
            
            // Initialize real-time data updates
            setupRealTimeDataUpdates()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setupCardClickListeners() {
        try {
            // Battery card click listener
            val batteryCard = findViewById<CardView>(R.id.batteryCard)
            batteryCard?.setOnClickListener {
                val intent = Intent(this, BatteryActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }

            // Security card click listener
            val securityCard = findViewById<CardView>(R.id.securityCard)
            securityCard?.setOnClickListener {
                val intent = Intent(this, SecurityActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }

            // PV card click listener
            val pvCard = findViewById<CardView>(R.id.pvCard)
            pvCard?.setOnClickListener {
                val intent = Intent(this, PVActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setupHorizontalNavigationListeners() {
        try {
            // Pump horizontal button
            val pumpCardHorizontal = findViewById<android.widget.LinearLayout>(R.id.pumpCard)
            pumpCardHorizontal?.setOnClickListener {
                val intent = Intent(this, PumpActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }

            // Battery horizontal button
            val batteryCardHorizontal = findViewById<android.widget.LinearLayout>(R.id.batteryCardHorizontal)
            batteryCardHorizontal?.setOnClickListener {
                val intent = Intent(this, BatteryActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }

            // PV horizontal button
            val pvCardHorizontal = findViewById<android.widget.LinearLayout>(R.id.pvCardHorizontal)
            pvCardHorizontal?.setOnClickListener {
                val intent = Intent(this, PVActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }

            // Security horizontal button
            val securityCardHorizontal = findViewById<android.widget.LinearLayout>(R.id.securityCardHorizontal)
            securityCardHorizontal?.setOnClickListener {
                val intent = Intent(this, SecurityActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }

            // Thresholds horizontal button
            val thresholdsCardHorizontal = findViewById<android.widget.LinearLayout>(R.id.thresholdsCardHorizontal)
            thresholdsCardHorizontal?.setOnClickListener {
                val intent = Intent(this, ThresholdsActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setupToolbarMenuListener() {
        try {
            val menuButton: ImageButton? = findViewById(R.id.menuButton)
            menuButton?.setOnClickListener {
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setupLanguageToggle() {
        try {
            val langBtn: ImageButton? = findViewById(R.id.btnLanguageDashboard)
            langBtn?.setOnClickListener {
                val current = AppCompatDelegate.getApplicationLocales().get(0)
                val next = if (current?.language == "fr") LocaleListCompat.forLanguageTags("en") else LocaleListCompat.forLanguageTags("fr")
                AppCompatDelegate.setApplicationLocales(next)
                recreate()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setupDashboardSwitch() {
        try {
            val systemSwitch = findViewById<android.widget.Switch>(R.id.dashboardSystemSwitch)
            val chargingStatus = findViewById<android.widget.TextView>(R.id.chargingStatusText)
            systemSwitch?.let { sw ->
                // Initialize label based on current state
                updateDashboardSwitchUI(sw.isChecked, chargingStatus, sw)
                sw.setOnCheckedChangeListener { _, isChecked ->
                    updateDashboardSwitchUI(isChecked, chargingStatus, sw)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun updateDashboardSwitchUI(
        isChecked: Boolean,
        chargingStatus: android.widget.TextView?,
        systemSwitch: android.widget.Switch
    ) {
        if (isChecked) {
            chargingStatus?.text = getString(R.string.charging)
            systemSwitch.text = getString(R.string.system_on)
        } else {
            chargingStatus?.text = getString(R.string.stopped_charging)
            systemSwitch.text = getString(R.string.system_off)
        }
    }

    private fun setupRealTimeDataUpdates() {
        try {
            val dataManager = (application as SmartSolarApplication).dataManager
            
            // Observe real-time data
            val powerText = findViewById<TextView>(R.id.powerGenerationText)
            val batteryText = findViewById<TextView>(R.id.batteryPercentageText)
            val connectionStatusText = findViewById<TextView>(R.id.connectionStatusText)

            // Observe real-time data
            lifecycleScope.launch {
                dataManager.power.collect { power ->
                    powerText.text = "${power.toInt()} W"
                }
            }

            lifecycleScope.launch {
                dataManager.batteryCharge.collect { battery ->
                    batteryText.text = "${battery.toInt()}%"
                }
            }

            lifecycleScope.launch {
                dataManager.connectionStatus.collect { connected ->
                    connectionStatusText.text = if (connected) "Connected" else "Disconnected"
                }
            }

            lifecycleScope.launch {
                dataManager.securityStatus.collect { security ->
                    // Update security status when UI elements are available
                }
            }

            lifecycleScope.launch {
                dataManager.pumpStatus.collect { pump ->
                    // Update pump status when UI elements are available
                }
            }
            
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    private fun updatePowerDisplay(power: Float) {
        try {
            // Update power generation display
            val powerText = findViewById<TextView>(R.id.powerGenerationText)
            powerText?.text = "${String.format("%.2f", power)} kW"
            
            // Update grid power supply (mock calculation)
            val gridPower = power * 0.2f // 20% of generated power goes to grid
            val gridText = findViewById<TextView>(R.id.gridPowerText)
            gridText?.text = "${String.format("%.2f", gridPower)} kW"
            
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    private fun updateBatteryDisplay(charge: Int) {
        try {
            val batteryText = findViewById<TextView>(R.id.batteryPercentageText)
            batteryText?.text = "$charge%"
            
            // Update battery card value
            val batteryCardText = findViewById<TextView>(R.id.batteryCardText)
            val batteryPower = charge * 0.12f // Convert percentage to kW
            batteryCardText?.text = "${String.format("%.1f", batteryPower)} kW"
            
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    private fun updateConnectionStatus(connected: Boolean) {
        try {
            val connectionStatus = findViewById<TextView>(R.id.connectionStatusText)
            if (connected) {
                connectionStatus?.text = getString(R.string.connected)
                connectionStatus?.setTextColor(resources.getColor(android.R.color.holo_green_light, null))
            } else {
                connectionStatus?.text = getString(R.string.disconnected)
                connectionStatus?.setTextColor(resources.getColor(android.R.color.holo_red_light, null))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    private fun updateSecurityStatus(status: String) {
        try {
            val securityCard = findViewById<CardView>(R.id.securityCard)
            if (status.contains("⚠️")) {
                // Show alert indicator
                securityCard?.setCardBackgroundColor(resources.getColor(android.R.color.holo_red_light, null))
            } else {
                // Normal status
                securityCard?.setCardBackgroundColor(resources.getColor(android.R.color.darker_gray, null))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    private fun updatePumpStatus(status: String) {
        try {
            val pumpCard = findViewById<android.widget.LinearLayout>(R.id.pumpCard)
            if (status.contains("⚠️")) {
                // Show alert indicator
                pumpCard?.setBackgroundColor(resources.getColor(android.R.color.holo_orange_light, null))
            } else {
                // Normal status
                pumpCard?.setBackgroundColor(resources.getColor(android.R.color.transparent, null))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        // Close the app when back is pressed on dashboard
        finishAffinity()
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
