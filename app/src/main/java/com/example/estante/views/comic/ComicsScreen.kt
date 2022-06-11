package com.example.estante.views.comic

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.estante.data.models.Collection
import com.example.estante.data.models.Comic
import com.example.estante.views.collection.CollectionItem
import com.example.estante.views.collection.CollectionList

@Composable
fun ComicsScreen(
    navController: NavController,
    comicListViewModel: ComicViewModel,
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("comic/-1")
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add a new Comic")

            }
        }
    ) {
        val comic by comicListViewModel.allComics.observeAsState(listOf())
        Column() {
            ComicList(
                comic,
                navController
            )
        }

    }
}

@Composable
fun ComicList(
    comics: List<Comic>,
    navController: NavController
) {
    LazyColumn() {
        items(comics) {
            ComicItem(it) {
                navController.navigate("comic/${it.comicId}")
            }
        }
    }
}

@Composable
fun ComicItem(
    comic: Comic,
    edit: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(10.dp)
            .clickable {
                expanded = !expanded
            }
    ) {
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.DarkGray)
                    .padding(0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                        .border(
                            width = 3.dp,
                            color = Color.LightGray,
                            shape = CircleShape
                        )
                        .size(75.dp)
                        .clip(CircleShape)
                        .background(Color.DarkGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${comic.comicId}",
                        style = MaterialTheme.typography.h3
                            .copy(color = Color.White, fontWeight = FontWeight.Normal)
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f),
                    text = comic.title,
                    style = MaterialTheme.typography.h5
                        .copy(color = Color.White, fontWeight = FontWeight.Bold)
                )
                if (expanded) {
                    Icon(
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 20.dp, 0.dp)
                            .size(32.dp)
                            .clickable { edit() },
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = Color.White
                    )
                }
            }
            if (expanded) {
                Column(
                    modifier = Modifier
                        .border(
                            width = 5.dp,
                            color = Color.DarkGray,
                            shape = RoundedCornerShape(2.dp)
                        )
                        .padding(12.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(bottom = 5.dp)
                                .weight(1f),
                            text = "ID: ${comic.comicId}",
                            style = MaterialTheme.typography.subtitle1.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    Text(
                        text = "Título: ${comic.title}",
                        style = MaterialTheme.typography.subtitle1.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "Editora: ${comic.publisher}",
                        style = MaterialTheme.typography.subtitle1.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}