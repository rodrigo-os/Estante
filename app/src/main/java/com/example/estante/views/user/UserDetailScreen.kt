package com.example.estante.views.user

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.estante.data.models.User

@Composable
fun UserSaveEditScreen(
    user: User,
    userViewModel: UserViewModel,
    navController: NavController,
    userViewModelSaveEdit: UserViewModelSaveEdit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (user.userId == -1) {
                    userViewModelSaveEdit.insert(userViewModel::insert)
                } else {
                    userViewModelSaveEdit.update(user.userId, userViewModel::update)
                }
                navController.popBackStack()
            }) {
                Icon(imageVector = Icons.Default.Done, contentDescription = "Confirm")
            }
        }
    ) {
        userViewModelSaveEdit.name.value = user.name
        userViewModelSaveEdit.collections.value = user.collections

        UserForm(
            userViewModelSaveEdit,
            userViewModel,
            user,
        ){
            navController.navigate("user")
        }
    }
}

@Composable
fun UserForm(
    userViewModelSaveEdit: UserViewModelSaveEdit,
    userViewModel: UserViewModel,
    user: User,
    navigateBack: () -> Unit,
){
    val name = userViewModelSaveEdit.name.observeAsState()
    val collections = userViewModelSaveEdit.collections.observeAsState()

    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 14.dp),
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp, start = 6.dp, end = 6.dp, top = 6.dp),
                label = {
                    Text(text = "Nome do Usuário")
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color.Yellow,
                    focusedBorderColor = Color.Yellow
                ),
                value = "${name.value}",
                onValueChange = {
                    userViewModelSaveEdit.name.value = it
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp, start = 6.dp, end = 6.dp),
                label = {
                    Text(text = "Coleções que pertencem ao Usuário")
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color.Yellow,
                    focusedBorderColor = Color.Yellow
                ),
                value = "${collections.value}",
                onValueChange = {
                    userViewModelSaveEdit.collections.value = it
                }
            )
        }
        if(user.userId != -1){
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    userViewModel.delete(user)
                    navigateBack()
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
