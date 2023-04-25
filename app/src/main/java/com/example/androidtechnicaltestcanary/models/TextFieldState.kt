package com.example.androidtechnicaltestcanary.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class TextFieldState {
    var value: String by mutableStateOf(value = String())
}