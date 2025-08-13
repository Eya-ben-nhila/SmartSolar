package com.example.smartsolar

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import com.google.android.material.button.MaterialButton
import android.content.Context

class SignUpActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContentView(R.layout.activity_signup)

        // Set up the toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Set up back button click listener
        val backButton: ImageButton = findViewById(R.id.btnBack)
        backButton.setOnClickListener {
            onBackPressed()
        }

        // Set up sign up button click listener
        val signUpButton: MaterialButton = findViewById(R.id.btnSignUp)
        signUpButton.setOnClickListener {
            // Navigate to DashboardActivity
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish() // Finish SignUpActivity so user can't go back to it with back button
        }
        
        updateTexts()
    }

    private fun updateTexts() {
        // Update all text elements based on current language
        // This ensures that texts set programmatically are also updated
        // Most text elements should already be using string resources in the layout
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
