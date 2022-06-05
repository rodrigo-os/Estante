package com.example.estante.views.collection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
                navController.navigate("collection")
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add a new Collection")

            }
        }
    ) {

        val collectionList by collectionListViewModel.allCollections.observeAsState(listOf())

        Column() {

            CollectionList(
                collections = collectionList,
                navController = navController
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
        items(collections) { collection ->

        }
    }
}