package com.example.smartsolar

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        android.util.Log.d("HomeActivity", "HomeActivity onCreate started")
        setContentView(R.layout.activity_home)
        android.util.Log.d("HomeActivity", "HomeActivity layout set")

        // Set up swipe hint click listener
        val swipeHint: TextView = findViewById(R.id.tvSwipeHint)
        swipeHint.setOnClickListener {
            navigateToLogin()
        }

        // Language toggle
        val languageBtn: ImageButton? = findViewById(R.id.btnLanguage)
        languageBtn?.setOnClickListener {
            toggleLanguage()
        }

        // Test MQTT Connection button
        val testMqttBtn: ImageButton? = findViewById(R.id.btnTestMqtt)
        testMqttBtn?.setOnClickListener {
            val intent = Intent(this, TestMqttConnectionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish() // Finish HomeActivity so user can't go back to it with back button
    }

    private fun toggleLanguage() {
        // Toggle between English and French using per-app locales
        val current = AppCompatDelegate.getApplicationLocales()
        val firstLang = if (current.isEmpty) "en" else current[0]?.language
        val next = if (firstLang == "fr") LocaleListCompat.forLanguageTags("en") else LocaleListCompat.forLanguageTags("fr")
        AppCompatDelegate.setApplicationLocales(next)
        // Force UI to refresh immediately
        recreate()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
