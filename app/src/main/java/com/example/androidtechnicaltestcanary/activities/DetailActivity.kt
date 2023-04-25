package com.example.androidtechnicaltestcanary.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.androidtechnicaltestcanary.models.data.cards.Card
import com.example.androidtechnicaltestcanary.ui.DetailScreen
import com.example.androidtechnicaltestcanary.ui.theme.AndroidTechnicalTestCanaryTheme
import com.google.gson.Gson

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cardAsGson = intent.getStringExtra("cardAsJson")
        val card = Gson().fromJson(cardAsGson, Card::class.java)

        setContent {
            AndroidTechnicalTestCanaryTheme {
                DetailScreen(card = card)
            }
        }
    }
}