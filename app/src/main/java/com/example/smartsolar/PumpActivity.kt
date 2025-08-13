package com.example.smartsolar

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import android.widget.ImageButton

class PumpActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContentView(R.layout.activity_pump)

        // Set up the toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Set up back button click listener
        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }

        // Set up Pump switch
        val pumpSwitch = findViewById<android.widget.Switch>(R.id.pumpSwitch)
        pumpSwitch.setOnCheckedChangeListener { _, isChecked ->
            pumpSwitch.text = if (isChecked) getString(R.string.system_off) else getString(R.string.system_on)
            
            // Animate alpha based on switch state
            pumpSwitch.animate()
                .alpha(if (isChecked) 0.6f else 1.0f)
                .setDuration(300)
                .start()

            // Update switch text color
            val textColor = if (isChecked)
                resources.getColor(android.R.color.darker_gray, theme)
            else
                android.graphics.Color.parseColor("#6AABD2")

            pumpSwitch.setTextColor(textColor)
        }
        
        updateTexts()
    }

    private fun updateTexts() {
        // Update all text elements based on current language
        val pumpSwitch = findViewById<android.widget.Switch>(R.id.pumpSwitch)
        
        // Update pump switch text
        pumpSwitch.text = if (pumpSwitch.isChecked) getString(R.string.system_off) else getString(R.string.system_on)
        
        // Update any other text elements that might be in the layout
        // Note: Most text elements should already be using string resources in the layout
        // This method ensures that programmatically set texts are also updated
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
} 