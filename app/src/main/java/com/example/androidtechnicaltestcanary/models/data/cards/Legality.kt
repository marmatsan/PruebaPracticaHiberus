package com.example.androidtechnicaltestcanary.models.data.cards

import kotlinx.serialization.Serializable

@Serializable
data class Legality(
    val format : String = "",
    val legality: String = ""
)
