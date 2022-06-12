package com.example.estante.views.user

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.estante.data.models.User

@Composable
fun UsersScreen(
    navController: NavController,
    userListViewModel: UserViewModel,
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("user/-1")
            }) {
                Icon(
                    modifier = Modifier
                        .size(30.dp),
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Add",
                    tint = Color.Black


                )
            }
        }
    ) {
        val user by userListViewModel.allUsers.observeAsState(listOf())

        Column() {
            UserList(
                user,
                navController
            )
        }
    }
}

@Composable
fun UserList(
    users: List<User>,
    navController: NavController
) {
    LazyColumn() {
        items(users) { user ->
            UserItem(user) {
                navController.navigate("user/${user.userId}")
            }
        }
    }
}

@Composable
fun UserItem(
    user: User,
    edit: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(start= 10.dp, top=  10.dp, end = 10.dp, bottom =  0.dp)
            .clickable {
                expanded = !expanded
            }
    ) {
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.DarkGray)
                    .padding(all = 5.dp),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .padding(all = 5.dp)
                        .border(
                            width = 1.dp,
                            color = Color.Cyan,
                            shape = AbsoluteRoundedCornerShape(10.dp)
                        )
                        .size(70.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${user.userId}",
                        style = MaterialTheme.typography.h3
                            .copy(
                                color = Color.White
                            )
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(all = 0.dp)
                        .weight(1f),
                    text = user.name,
                    style = MaterialTheme.typography.h5
                        .copy(
                            color = Color.White
                        )
                )
                if (expanded) {
                    Icon(
                        modifier = Modifier
                            .padding(all = 5.dp)
                            .size(30.dp)
                            .clickable { edit() },
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Edit",
                        tint = Color.White
                    )
                }
            }
            if (expanded) {
                Column(
                    modifier = Modifier
                        .border(
                            width = 10.dp,
                            color = Color.DarkGray,
                        )
                        .padding(all=10.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(all = 8.dp)
                    ) {
                        Text(
                            text = "Nome do Usuário: ${user.name}",
                            style = MaterialTheme.typography.subtitle2.copy(
                                color = Color.White
                            )
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(all = 8.dp)
                    ){
                        Text(
                            text = "Coleções: ${user.collections}",
                            style = MaterialTheme.typography.subtitle2.copy(
                                color = Color.White
                            )
                        )
                    }
                }
            }
        }
    }
}