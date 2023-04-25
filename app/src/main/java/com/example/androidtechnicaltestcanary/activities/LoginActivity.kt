package com.example.androidtechnicaltestcanary.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.androidtechnicaltestcanary.ui.LoginScreen
import com.example.androidtechnicaltestcanary.ui.theme.AndroidTechnicalTestCanaryTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidTechnicalTestCanaryTheme {
                LoginScreen()
            }
        }
    }
}