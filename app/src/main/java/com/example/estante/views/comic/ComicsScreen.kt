package com.example.estante.views.comic

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
import com.example.estante.data.models.Comic

@Composable
fun ComicsScreen(
    navController: NavController,
    comicListViewModel: ComicViewModel,
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("comic")
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add a new Comic")

            }
        }
    ) {

        val comicList by comicListViewModel.allComics.observeAsState(listOf())

        Column() {

            ComicList(
                comics = comicList,
                navController = navController
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
        items(comics) { comic ->

        }
    }
}