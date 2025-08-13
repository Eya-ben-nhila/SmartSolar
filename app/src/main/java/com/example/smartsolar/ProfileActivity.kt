package com.example.smartsolar

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import com.google.android.material.button.MaterialButton
import androidx.appcompat.widget.Toolbar

class ProfileActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContentView(R.layout.activity_profile_final)

        // Set up the toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        
        // Set up the navigation icon (back button) in the toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(android.R.drawable.ic_menu_revert)
        
        updateTexts()

        // Logout button
        val logoutBtn: MaterialButton? = findViewById(R.id.btn_logout)
        logoutBtn?.setOnClickListener {
            performLogout()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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
