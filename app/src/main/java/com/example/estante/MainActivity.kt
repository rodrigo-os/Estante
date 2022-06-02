package com.example.estante

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.estante.ui.theme.EstanteTheme
import com.example.estante.views.collection.CollectionViewModel
import com.example.estante.views.collection.CollectionViewModelFactory
import com.example.estante.views.collection.CollectionsScreen
import com.example.estante.views.comic.ComicViewModel
import com.example.estante.views.comic.ComicViewModelFactory
import com.example.estante.views.comic.ComicsScreen
import com.example.estante.views.user.UserViewModel
import com.example.estante.views.user.UserViewModelFactory
import com.example.estante.views.user.UsersScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userViewModel: UserViewModel by viewModels<UserViewModel> {
            UserViewModelFactory(
                (this.applicationContext as BookshelfApplication).bookshelfDatabase.userDao()
            )
        }

        val collectionViewModel: CollectionViewModel by viewModels<CollectionViewModel> {
            CollectionViewModelFactory(
                (this.applicationContext as BookshelfApplication).bookshelfDatabase.collectionDao()
            )
        }
        val comicViewModel: ComicViewModel by viewModels<ComicViewModel> {
            ComicViewModelFactory(
                (this.applicationContext as BookshelfApplication).bookshelfDatabase.comicDao()
            )
        }

        setContent {
            EstanteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BookshelfApp(
                        userViewModel,
                        collectionViewModel,
                        comicViewModel,
                    )
                }
            }
        }
    }
}

@Composable
fun BookshelfApp(
    userViewModel: UserViewModel,
    collectionViewModel: CollectionViewModel,
    comicViewModel: ComicViewModel,
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(80.dp)
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                bottomNavScreens.forEach { botNavScreen ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                modifier = Modifier.size(50.dp),
                                painter = painterResource(id = botNavScreen.icon),
                                contentDescription = stringResource(id = botNavScreen.name)
                            )
                        },
                        label = { Text(text = stringResource(id = botNavScreen.name)) },
                        selected = currentDestination?.hierarchy?.any {
                            it.route == botNavScreen.route
                        } == true,
                        onClick = {
                            navController.navigate(botNavScreen.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            startDestination = Screen.UserScreen.route
        ) {
            composable(Screen.UserScreen.route) {
                UsersScreen()
            }
            composable(Screen.CollectionScreen.route) {
                CollectionsScreen()
            }
            composable(Screen.ComicScreen.route) {
                ComicsScreen()
            }
            composable(Screen.UserDetails.route) {

            }
            composable(Screen.CollectionDetails.route) {

            }
            composable(Screen.ComicDetails.route) {

            }
        }
    }
}

private val bottomNavScreens = listOf(
    Screen.UserScreen,
    Screen.CollectionScreen,
    Screen.ComicScreen
)


sealed class Screen(
    val route: String,
    @DrawableRes val icon: Int,
    @StringRes val name: Int,
) {
    object UserScreen : Screen("user", R.drawable.user_icon, R.string.user)
    object CollectionScreen :
        Screen("collection", R.drawable.collection_icon, R.string.collection)
    object ComicScreen : Screen("comic", R.drawable.comic_icon, R.string.comic)


    object UserDetails : Screen("user_details", R.drawable.user_icon, R.string.user)
    object CollectionDetails :
        Screen("collection_details", R.drawable.collection_icon, R.string.collection)
    object ComicDetails : Screen("comic_details", R.drawable.comic_icon, R.string.comic)
}