package com.example.estante.views.character

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.estante.data.models.Character

@Composable
fun CharacterSaveEditScreen(
    character: Character,
    characterViewModel: CharacterViewModel,
    navController: NavController,
    addEditCharacterViewModel: AddEditCharacterViewModel
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (character.characterId == -1) {
                    addEditCharacterViewModel.insert(characterViewModel::insert)
                } else {
                    addEditCharacterViewModel.update(
                        character.characterId,
                        characterViewModel::update
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
        addEditCharacterViewModel.name.value = character.name
        addEditCharacterViewModel.creators.value = character.creators
        CharacterForm(
            addEditCharacterViewModel,
            characterViewModel,
            character,
        ){
            navController.navigate("character")
        }
    }
}

@Composable
fun CharacterForm(
    addEditCharacterViewModel: AddEditCharacterViewModel,
    characterViewModel: CharacterViewModel,
    character: Character,
    navigateBack: () -> Unit,
){
    val name = addEditCharacterViewModel.name.observeAsState()
    val creators = addEditCharacterViewModel.creators.observeAsState()

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
                    Text(text = "Nome do Personagem")
                },
                value = "${name.value}",
                onValueChange = {
                    addEditCharacterViewModel.name.value = it
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp, start = 8.dp, end = 8.dp, top = 10.dp),
                label = {
                    Text(text = "Criadores")
                },
                value = "${creators.value}",
                onValueChange = {
                    addEditCharacterViewModel.creators.value = it
                }
            )
        }
        if(character.characterId != -1){
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    characterViewModel.delete(character)
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