package com.example.smartsolar

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.CheckBox
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.material.button.MaterialButton

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Set up the toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Set up back button click listener
        val backButton: ImageButton = findViewById(R.id.btnBack)
        backButton.setOnClickListener {
            onBackPressed()
        }

        // Pre-fill if remembered
        val emailTil = findViewById<TextInputLayout>(R.id.tilEmail)
        val passwordTil = findViewById<TextInputLayout>(R.id.tilPassword)
        val emailField = emailTil.editText as? TextInputEditText
        val passwordField = passwordTil.editText as? TextInputEditText
        val rememberCheck = findViewById<CheckBox>(R.id.cbRememberMe)
        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
        val remembered = prefs.getBoolean("remember", false)
        if (remembered) {
            emailField?.setText(prefs.getString("email", ""))
            passwordField?.setText(prefs.getString("password", ""))
            rememberCheck?.isChecked = true
        }

        // Set up login button click listener
        val loginButton: MaterialButton = findViewById(R.id.btnSignIn)
        loginButton.setOnClickListener {
            val email = emailField?.text?.toString()?.trim().orEmpty()
            val password = passwordField?.text?.toString()?.trim().orEmpty()

            if (!isValidEmail(email)) {
                Toast.makeText(this, getString(R.string.invalid_email), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.length < 6) {
                Toast.makeText(this, getString(R.string.password_too_short), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val remember = rememberCheck?.isChecked == true
            prefs.edit()
                .putBoolean("remember", remember)
                .apply()
            if (remember) {
                prefs.edit()
                    .putString("email", email)
                    .putString("password", password)
                    .apply()
            } else {
                prefs.edit().remove("email").remove("password").apply()
            }

            // TODO: plug real auth API here
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }

        // Set up sign up button click listener
        val signUpButton: MaterialButton = findViewById(R.id.btnGoogle)
        signUpButton.setOnClickListener {
            // Navigate to SignUpActivity
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}

private fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
