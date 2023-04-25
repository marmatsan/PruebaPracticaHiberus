package com.example.androidtechnicaltestcanary.ui

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.androidtechnicaltestcanary.models.data.cards.Card

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(card: Card) {
    Scaffold(topBar = {
        AppBar()
    }) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            CardDetails(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                card = card
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    val dispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        dispatcher?.onBackPressed()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back to Master Screen",
                        tint = Color.Black
                    )
                }
            }
        }
    )
}

@Composable
fun CardName(name: String, modifier: Modifier) {
    Text(text = name, modifier = modifier, fontWeight = FontWeight.Bold, fontSize = 26.sp)
}

@Composable
fun CardRarity(rarity: String, modifier: Modifier) {
    Text(text = rarity, modifier = modifier.alpha(alpha = 0.5F), fontSize = 17.sp)
}

@Composable
fun CardText(text: String, modifier: Modifier) {
    Text(
        text = text,
        modifier = modifier.alpha(alpha = 0.5F),
        fontSize = 15.sp,
        textAlign = TextAlign.Justify
    )
}

@Composable
fun CardDetails(modifier: Modifier, card: Card) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.padding(8.dp))
        AsyncImage(
            model = card.imageURL,
            contentDescription = "Card Image",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(size = 220.dp)
        )
        Spacer(modifier = Modifier.padding(4.dp))
        CardName(
            name = card.name,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.padding(5.dp))
        CardRarity(rarity = card.rarity, modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.padding(16.dp))
        Divider(color = Color.Gray, thickness = 0.5.dp)
        Spacer(modifier = Modifier.padding(10.dp))
        CardText(
            text = card.text,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(start = 20.dp, end = 20.dp)
        )
    }
}