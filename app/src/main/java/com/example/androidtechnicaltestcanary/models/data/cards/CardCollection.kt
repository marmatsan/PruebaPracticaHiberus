package com.example.androidtechnicaltestcanary.models.data.cards

import kotlinx.serialization.Serializable

@Serializable
data class CardCollection (
    val cards: List<Card> = emptyList()
)