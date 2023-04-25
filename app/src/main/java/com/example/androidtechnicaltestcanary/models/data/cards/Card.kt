package com.example.androidtechnicaltestcanary.models.data.cards

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Card(
    val name: String = "",
    val manaCost: String = "",
    val cmc: Double = 0.0,
    val colors: List<String> = emptyList(),
    val colorIdentity: List<String> = emptyList(),
    val type: String = "",
    val supertypes: List<String> = emptyList(),
    val types: List<String> = emptyList(),
    val subtypes: List<String> = emptyList(),
    val rarity: String = "",
    val set: String = "",
    val setName: String = "",
    val text: String = "",
    val flavor: String = "",
    val artist: String = "",
    val number: String = "",
    val power: String = "",
    val toughness: String = "",
    val layout: String = "",
    val multiverseid: String = "",

    @SerialName("imageUrl")
    val imageURL: String = "",
    val rulings: List<Rule> = emptyList(),
    val variations: List<String> = emptyList(),
    val foreignNames: List<ForeignName> = emptyList(),
    val printings: List<String> = emptyList(),
    val originalText: String = "",
    val originalType: String = "",
    val id: String = "",
    val legalities: List<Legality> = emptyList()
)