package com.example.estante.views.user

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.estante.data.models.User

@Composable
fun UserSaveEditScreen(
    user: User,
    userViewModel: UserViewModel,
    navController: NavController,
    addEditUserViewModel: AddEditUserViewModel
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (user.userId == -1) {
                    addEditUserViewModel.insert(userViewModel::insert)
                } else {
                    addEditUserViewModel.update(
                        user.userId,
                        userViewModel::update
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
        addEditUserViewModel.name.value = user.name
        addEditUserViewModel.collections.value = user.collections
        UserForm(
            addEditUserViewModel,
            userViewModel,
            user,
        ){
            navController.navigate("user")
        }
    }
}

@Composable
fun UserForm(
    addEditUserViewModel: AddEditUserViewModel,
    userViewModel: UserViewModel,
    user: User,
    navigateBack: () -> Unit,
){
    val name = addEditUserViewModel.name.observeAsState()
    val collections = addEditUserViewModel.collections.observeAsState()

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
                    Text(text = "Nome do Usuário")
                },
                value = "${name.value}",
                onValueChange = {
                    addEditUserViewModel.name.value = it
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp, start = 8.dp, end = 8.dp, top = 10.dp),
                label = {
                    Text(text = "Coleções que pertencem ao Usuário")
                },
                value = "${collections.value}",
                onValueChange = {
                    addEditUserViewModel.collections.value = it
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
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Delete"
                )
            }
        }
    }
}