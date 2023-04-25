package com.example.androidtechnicaltestcanary.models.data.cards

import kotlinx.serialization.Serializable

@Serializable
data class Rule(
    val date : String = "",
    val text: String = ""
)
