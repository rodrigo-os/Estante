package com.example.estante.views.collection

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

@Composable
fun CollectionsScreen(
    navController: NavController,
    collectionListViewModel: CollectionViewModel,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("collection/-1")
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add a new Collection")

            }
        }
    ) {
        val collection by collectionListViewModel.allCollections.observeAsState(listOf())

        Column() {
            CollectionList(
                collection,
                navController
            )
        }

    }
}

@Composable
fun CollectionList(
    collections: List<Collection>,
    navController: NavController
) {
    LazyColumn() {
        items(collections) {
            CollectionItem(it) {
                navController.navigate("collection/${it.collectionId}")
            }
        }
    }
}

@Composable
fun CollectionItem(
    collection: Collection,
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
                        text = "${collection.collectionId}",
                        style = MaterialTheme.typography.h3
                            .copy(color = Color.White, fontWeight = FontWeight.Normal)
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f),
                    text = collection.collectionName,
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
                            text = "ID: ${collection.collectionId}",
                            style = MaterialTheme.typography.subtitle1.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    Text(
                        text = "Nome: ${collection.collectionName}",
                        style = MaterialTheme.typography.subtitle1.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "Lembrete: ${collection.note}",
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