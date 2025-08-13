package com.example.smartsolar

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton

class MenuActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Set up back button click listener
        val backButton: ImageButton = findViewById(R.id.btnMenu)
        backButton?.setOnClickListener {
            onBackPressed()
        }

        // Set up menu item click listeners
        val menuHome = findViewById<android.view.View>(R.id.menuHome)
        menuHome?.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        val menuPV = findViewById<android.view.View>(R.id.menuPV)
        menuPV?.setOnClickListener {
            val intent = Intent(this, PVActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        val menuControl = findViewById<android.view.View>(R.id.menuControl)
        menuControl?.setOnClickListener {
            val intent = Intent(this, PumpActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        val menuBattery = findViewById<android.view.View>(R.id.menuBattery)
        menuBattery?.setOnClickListener {
            val intent = Intent(this, BatteryActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        val menuSecurity = findViewById<android.view.View>(R.id.menuSecurity)
        menuSecurity?.setOnClickListener {
            val intent = Intent(this, SecurityActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        val menuHistory = findViewById<android.view.View>(R.id.menuHistory)
        menuHistory?.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        val menuThresholds = findViewById<android.view.View>(R.id.menuThresholds)
        menuThresholds?.setOnClickListener {
            val intent = Intent(this, ThresholdsActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        // Subscriptions menu removed per requirement

        val profileLayout = findViewById<android.view.View>(R.id.profileLayout)
        profileLayout?.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        // Logout via settings power icon
        val logoutButton = findViewById<android.view.View>(R.id.btnSettings)
        logoutButton?.setOnClickListener {
            performLogout()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
