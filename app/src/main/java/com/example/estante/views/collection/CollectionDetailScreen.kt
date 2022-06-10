package com.example.estante.views.collection

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
import com.example.estante.data.models.Collection

@Composable
fun CollectionSaveEditScreen(
    collection: Collection,
    collectionViewModel: CollectionViewModel,
    navController: NavController,
    collectionViewModelSaveEdit: CollectionViewModelSaveEdit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (collection.collectionId == -1) {
                    collectionViewModelSaveEdit.insert(collectionViewModel::insert)
                } else {
                    collectionViewModelSaveEdit.update(
                        collection.collectionId,
                        collectionViewModel::update
                    )
                }
                navController.popBackStack()
            }) {
                Icon(imageVector = Icons.Default.Done, contentDescription = "Confirm")
            }
        }
    ) {
        collectionViewModelSaveEdit.name.value = collection.collectionName
        collectionViewModelSaveEdit.note.value = collection.note

        CollectionForm(
            collectionViewModelSaveEdit,
            collectionViewModel,
            collection
        ) {
            navController.navigate("collection")
        }
    }
}

@Composable
fun CollectionForm(
    collectionViewModelSaveEdit: CollectionViewModelSaveEdit,
    collectionViewModel: CollectionViewModel,
    collection: Collection,
    navBack: () -> Unit
) {
    val name = collectionViewModelSaveEdit.name.observeAsState()
    val note = collectionViewModelSaveEdit.note.observeAsState()

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
                    Text(text = "Nome da Coleção")
                },
                value = "${name.value}",
                onValueChange = {
                    collectionViewModelSaveEdit.name.value = it
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp, start = 8.dp, end = 8.dp, top = 10.dp),
                label = {
                    Text(text = "Lembrete")
                },
                value = "${note.value}",
                onValueChange = {
                    collectionViewModelSaveEdit.note.value = it
                }
            )
        }
        if (collection.collectionId != -1) {
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    collectionViewModel.delete(collection)
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