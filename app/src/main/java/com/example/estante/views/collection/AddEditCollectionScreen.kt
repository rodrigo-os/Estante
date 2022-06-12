package com.example.estante.views.collection

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
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
    addEditCollectionViewModel: AddEditCollectionViewModel
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (collection.collectionId == -1) {
                    addEditCollectionViewModel.insert(collectionViewModel::insert)
                } else {
                    addEditCollectionViewModel.update(
                        collection.collectionId,
                        collectionViewModel::update
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
        addEditCollectionViewModel.name.value = collection.collectionName
        addEditCollectionViewModel.note.value = collection.note
        CollectionForm(
            addEditCollectionViewModel,
            collectionViewModel,
            collection
        ) {
            navController.navigate("collection")
        }
    }
}

@Composable
fun CollectionForm(
    addEditCollectionViewModel: AddEditCollectionViewModel,
    collectionViewModel: CollectionViewModel,
    collection: Collection,
    navigateBack: () -> Unit
) {
    val name = addEditCollectionViewModel.name.observeAsState()
    val note = addEditCollectionViewModel.note.observeAsState()

    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 10.dp),
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 10.dp),
                label = {
                    Text(text = "Nome da Coleção")
                },
                value = "${name.value}",
                onValueChange = {
                    addEditCollectionViewModel.name.value = it
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 10.dp),
                label = {
                    Text(text = "Lembrete")
                },
                value = "${note.value}",
                onValueChange = {
                    addEditCollectionViewModel.note.value = it
                }
            )
        }
        if (collection.collectionId != -1) {
            FloatingActionButton(
                modifier = Modifier.padding(all = 16.dp),
                onClick = {
                    collectionViewModel.delete(collection)
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