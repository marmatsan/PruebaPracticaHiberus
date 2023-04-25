package com.example.androidtechnicaltestcanary.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.androidtechnicaltestcanary.models.MainViewModel
import com.example.androidtechnicaltestcanary.ui.MasterScreen
import com.example.androidtechnicaltestcanary.ui.theme.AndroidTechnicalTestCanaryTheme

class MasterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainViewModel: MainViewModel by viewModels()
        setContent {
            AndroidTechnicalTestCanaryTheme {
                MasterScreen(mainViewModel)
            }
        }
    }
}