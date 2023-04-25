package com.example.androidtechnicaltestcanary.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.androidtechnicaltestcanary.models.data.cards.Card

@Composable
fun DirectoryItem(card: Card) {
    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CardImage(imageURL = card.imageURL)
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
        ) {
            Text(text = card.name, fontSize = 18.sp)
            Spacer(modifier = Modifier.padding(2.dp))
            Text(text = card.rarity, color = Color.Gray)
        }
    }
}

@Composable
fun CardImage(
    imageURL : String,
    modifier: Modifier = Modifier,
    size: Dp = 50.dp,
) {
    Box(modifier.size(size), contentAlignment = Alignment.Center) {
        AsyncImage(
            model = imageURL,
            contentDescription = "Card Image"
        )
    }
}

