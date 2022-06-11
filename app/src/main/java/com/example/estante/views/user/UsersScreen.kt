package com.example.estante.views.user

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.example.estante.data.models.User

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

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
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add a new User")

            }
        }
    ) {

        val users by userListViewModel.allUsers.observeAsState(listOf())


        Column() {

            UserList(
                users = users,
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
            .padding(3.dp)
            .clickable {
                expanded = !expanded
            }
            .animateContentSize(
                spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.DarkGray)
                    .padding(0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(6.dp)
                        .border(
                            width = 4.dp,
                            color = Color.Green,
                            shape = CircleShape
                        )
                        .size(75.dp)
                        .clip(CircleShape)
                        .background(Color.DarkGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${user.userId}",
                        style = MaterialTheme.typography.h3
                            .copy(color = Color.White, fontWeight = FontWeight.Normal)
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f),
                    text = user.name,
                    style = MaterialTheme.typography.h5
                        .copy(color = Color.White, fontWeight = FontWeight.Bold)
                )
                if (expanded) {
                    Icon(
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 20.dp, 0.dp)
                            .size(32.dp)
                            .clickable { edit() },
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = Color.Yellow
                    )
                }
            }
            if (expanded) {
                Column(
                    modifier = Modifier
                        .border(
                            width = 5.dp,
                            color = Color.DarkGray,
                            shape = RoundedCornerShape(2.dp)
                        )
                        .padding(12.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(bottom = 1.dp)
                                .weight(1f),
                            text = "ID: ${user.userId}",
                            style = MaterialTheme.typography.subtitle1.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            modifier = Modifier
                                .padding(bottom = 5.dp)
                                .weight(0.9f),
                            text = "Nome do Usuário: ${user.name}",
                            style = MaterialTheme.typography.subtitle1.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(bottom = 6.dp)
                                .weight(1f),
                            text = "Coleções: ${user.collections}",
                            style = MaterialTheme.typography.subtitle1.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }
}