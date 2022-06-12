package com.example.estante.views.comic

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
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
    addEditComicViewModel: AddEditComicViewModel
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (comic.comicId == -1) {
                    addEditComicViewModel.insert(comicViewModel::insert)
                } else {
                    addEditComicViewModel.update(
                        comic.comicId,
                        comicViewModel::update
                    )
                }
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Outlined.Done,
                    contentDescription = "Done"
                )
            }
        }
    ) {
        addEditComicViewModel.title.value = comic.title
        addEditComicViewModel.publisher.value = comic.publisher
        ComicForm(
            addEditComicViewModel,
            comicViewModel,
            comic
        ) {
            navController.navigate("comic")
        }
    }
}

@Composable
fun ComicForm(
    addEditComicViewModel: AddEditComicViewModel,
    comicViewModel: ComicViewModel,
    comic: Comic,
    navigateBack: () -> Unit
) {
    val title = addEditComicViewModel.title.observeAsState()
    val publisher = addEditComicViewModel.publisher.observeAsState()

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
                    .padding(all = 10.dp),
                label = {
                    Text(text = "Título da Publicação")
                },
                value = "${title.value}",
                onValueChange = {
                    addEditComicViewModel.title.value = it
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
                    addEditComicViewModel.publisher.value = it
                }
            )
        }
        if (comic.comicId != -1) {
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    comicViewModel.delete(comic)
                    navigateBack()
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Delete"
                )
            }
        }
    }
}