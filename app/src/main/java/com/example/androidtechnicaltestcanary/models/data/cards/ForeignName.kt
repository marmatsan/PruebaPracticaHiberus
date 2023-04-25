package com.example.androidtechnicaltestcanary.models.data.cards

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForeignName (
    val name: String = "",
    val text: String = "",
    val type: String = "",
    val flavor: String? = "",

    @SerialName("imageUrl")
    val imageURL: String = "",

    val language: String = "",
    val multiverseid: Long = 0L
)