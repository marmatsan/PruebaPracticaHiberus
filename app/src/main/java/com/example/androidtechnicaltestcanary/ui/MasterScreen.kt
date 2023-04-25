package com.example.androidtechnicaltestcanary.ui

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidtechnicaltestcanary.R
import com.example.androidtechnicaltestcanary.activities.DetailActivity
import com.example.androidtechnicaltestcanary.activities.LoginActivity
import com.example.androidtechnicaltestcanary.models.MainViewModel
import com.example.androidtechnicaltestcanary.models.SearchWidgetState
import com.example.androidtechnicaltestcanary.models.data.cards.Card
import com.example.androidtechnicaltestcanary.models.data.cards.CardCollection
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MasterScreen(mainViewModel: MainViewModel) {

    val searchWidgetState by mainViewModel.searchWidgetState
    val searchTextState by mainViewModel.searchTextState

    Scaffold(topBar = {
        MainAppBar(
            searchWidgetState = searchWidgetState,
            searchTextState = searchTextState,
            onTextChange = {
                mainViewModel.updateSearchTextState(newValue = it)
            },
            onCloseClicked = {
                mainViewModel.updateSearchTextState(newValue = "")
                mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
            },
            onSearchTriggered = {
                mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
            }
        )
    }) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            DirectoryContainer(searchTextState = searchTextState)
        }
    }
}

@Composable
fun MainAppBar(
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchTriggered: () -> Unit
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            AppBar(onSearchClicked = onSearchTriggered)
        }

        SearchWidgetState.OPENED -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(onSearchClicked: () -> Unit) {
    var showMenu by remember { mutableStateOf(false) }
    val context = LocalContext.current
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.img_topappbar_logo),
                    contentDescription = "TopAppBarColor",
                    modifier = Modifier
                        .height(24.dp)
                        .padding(top = 2.dp, end = 24.dp),
                    tint = Color.Unspecified
                )
                Text(
                    text = "Mazo de cartas"
                )
            }
        },
        actions = {
            IconButton(
                onClick = { onSearchClicked() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon"
                )
            }
            IconButton(onClick = {
                showMenu = true
            }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Open Options"
                )
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text(text = "Salir") },
                    onClick = {
                        context.startActivity(
                            Intent(
                                context,
                                LoginActivity::class.java
                            ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back to Master Screen",
                            tint = Color.Black
                        )
                    }
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        color = Color.White
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(), value = text, onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier.alpha(0.5F),
                    text = "Busca por nombre o rareza de carta...",
                    color = Color.Black
                )
            },
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Color.Black
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Search Icon",
                        tint = Color.Black
                    )
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DirectoryContainer(searchTextState: String) {
    val currentContext = LocalContext.current
    var cardCollection = CardCollection()

    runBlocking {
        val (_, _, result) = Fuel.get(path = "https://api.magicthegathering.io/v1/cards")
            .awaitStringResponseResult()
        result.fold(
            { data ->
                run {
                    val json = Json { allowStructuredMapKeys = true; encodeDefaults = true }
                    cardCollection = json.decodeFromString(
                        CardCollection.serializer(),
                        data
                    )
                }
            },
            { error -> println("Error de tipo ${error.exception}: ${error.message}") }
        )
    }

    LazyColumn {
        val grouped = cardCollection.cards.groupBy(Card::type)

        grouped.forEach { (header, items) ->
            val filteredItems = items.filter { card ->
                card.name.contains(searchTextState, ignoreCase = true) || card.rarity.contains(
                    searchTextState,
                    ignoreCase = true
                )
            }
            if (filteredItems.isNotEmpty()) {
                stickyHeader {
                    Text(
                        text = header,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(start = 44.dp, top = 18.dp, bottom = 12.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
                items(items = filteredItems) { card ->
                    if (card.imageURL.isNotEmpty()) {
                        Surface(modifier = Modifier.clickable {
                            val cardAsJson = Gson().toJson(card)
                            val intent = Intent(currentContext, DetailActivity::class.java)
                                .putExtra("cardAsJson", cardAsJson)
                            currentContext.startActivity(intent)
                        }) {
                            DirectoryItem(card = card)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewDirectoryScreen() {
    MasterScreen(mainViewModel = MainViewModel())
}