package com.example.estante.views.comic

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.estante.data.models.Comic

@Composable
fun ComicSaveEditScreen(
    comic: Comic,
    comicViewModel: ComicViewModel,
    navController: NavController,
    comicViewModelSaveEdit: ComicViewModelSaveEdit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (comic.comicId == -1) {
                    comicViewModelSaveEdit.insert(comicViewModel::insert)
                } else {
                    comicViewModelSaveEdit.update(
                        comic.comicId,
                        comicViewModel::update
                    )
                }
                navController.popBackStack()
            }) {
                Icon(imageVector = Icons.Default.Done, contentDescription = "Confirm")
            }
        }
    ) {
        comicViewModelSaveEdit.title.value = comic.title
        comicViewModelSaveEdit.publisher.value = comic.publisher

        ComicForm(
            comicViewModelSaveEdit,
            comicViewModel,
            comic
        ) {
            navController.navigate("comic")
        }
    }
}

@Composable
fun ComicForm(
    comicViewModelSaveEdit: ComicViewModelSaveEdit,
    comicViewModel: ComicViewModel,
    comic: Comic,
    navBack: () -> Unit
) {
    val title = comicViewModelSaveEdit.title.observeAsState()
    val publisher = comicViewModelSaveEdit.publisher.observeAsState()

    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp, start = 8.dp, end = 8.dp, top = 10.dp),
                label = {
                    Text(text = "Título")
                },
                value = "${title.value}",
                onValueChange = {
                    comicViewModelSaveEdit.title.value = it
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp, start = 8.dp, end = 8.dp, top = 10.dp),
                label = {
                    Text(text = "Editora")
                },
                value = "${publisher.value}",
                onValueChange = {
                    comicViewModelSaveEdit.publisher.value = it
                }
            )
        }
        if (comic.comicId != -1) {
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    comicViewModel.delete(comic)
                    navBack()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }
        }
    }
}