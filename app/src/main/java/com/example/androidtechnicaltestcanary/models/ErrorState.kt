package com.example.androidtechnicaltestcanary.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class ErrorState {
    var value: Boolean by mutableStateOf(value = false)
}