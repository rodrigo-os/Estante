package com.example.estante.views.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.example.estante.data.models.User

import androidx.compose.foundation.lazy.items

@Composable
fun UsersScreen(
    navController: NavController,
    userListViewModel: UserViewModel,
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("user")
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add a new User")

            }
        }
    ) {

        val userList by userListViewModel.allUsers.observeAsState(listOf())

        Column() {

            UserList(
                users = userList,
                navController = navController
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

        }
    }
}