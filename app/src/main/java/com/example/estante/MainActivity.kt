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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.estante.ui.theme.EstanteTheme
import com.example.estante.views.collection.*
import com.example.estante.views.comic.*
import com.example.estante.views.user.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userViewModel: UserViewModel by viewModels<UserViewModel> {
            UserViewModelFactory(
                (this.applicationContext as BookshelfApplication).bookshelfDatabase.userDao()
            )
        }
        val addEditUserViewModel: AddEditUserViewModel by viewModels()
        addEditUserViewModel.startBy(userViewModel.getStartPoint())
        val collectionViewModel: CollectionViewModel by viewModels<CollectionViewModel> {
            CollectionViewModelFactory(
                (this.applicationContext as BookshelfApplication).bookshelfDatabase.collectionDao()
            )
        }
        val addEditCollectionViewModel: AddEditCollectionViewModel by viewModels()
        addEditCollectionViewModel.startBy(collectionViewModel.getStartPoint())

        val comicViewModel: ComicViewModel by viewModels<ComicViewModel> {
            ComicViewModelFactory(
                (this.applicationContext as BookshelfApplication).bookshelfDatabase.comicDao()
            )
        }
        val addEditComicViewModel: AddEditComicViewModel by viewModels()
        addEditComicViewModel.startBy(comicViewModel.getStartPoint())


        setContent {
            EstanteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BookshelfApp(
                        userViewModel,
                        addEditUserViewModel,
                        collectionViewModel,
                        addEditCollectionViewModel,
                        comicViewModel,
                        addEditComicViewModel,
                    )
                }
            }
        }
    }
}

@Composable
fun BookshelfApp(
    userViewModel: UserViewModel,
    addEditUserViewModel: AddEditUserViewModel,
    collectionViewModel: CollectionViewModel,
    addEditCollectionViewModel: AddEditCollectionViewModel,
    comicViewModel: ComicViewModel,
    addEditComicViewModel: AddEditComicViewModel
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
                UsersScreen(navController, userViewModel)
            }

            composable(
                route = "user/{userId}",
                arguments = listOf(navArgument("userId") {
                    defaultValue = -1
                    type = NavType.IntType
                })
            ) {
                val user = userViewModel.getUser(
                    it.arguments?.getInt("userId") ?: -1
                )
                UserSaveEditScreen(
                    user = user,
                    userViewModel = userViewModel,
                    navController = navController,
                    addEditUserViewModel = addEditUserViewModel
                )
            }

            composable(Screen.UserDetails.route) {

            }

            composable(Screen.CollectionScreen.route) {
                CollectionsScreen(navController, collectionViewModel)
            }

            composable(
                route = "collection/{collectionId}",
                arguments = listOf(navArgument("collectionId") {
                    defaultValue = -1
                    type = NavType.IntType
                })
            ) {
                val collection = collectionViewModel.getCollection(
                    it.arguments?.getInt("collectionId") ?: -1
                )
                CollectionSaveEditScreen(
                    collection = collection,
                    collectionViewModel = collectionViewModel,
                    navController = navController,
                    addEditCollectionViewModel = addEditCollectionViewModel
                )
            }
            composable(Screen.CollectionDetails.route) {

            }

            composable(Screen.ComicScreen.route) {
                ComicsScreen(navController, comicViewModel)
            }

            composable(
                route = "comic/{comicId}",
                arguments = listOf(navArgument("comicId") {
                    defaultValue = -1
                    type = NavType.IntType
                })
            ) {
                val comic = comicViewModel.getComic(
                    it.arguments?.getInt("comicId") ?: -1
                )
                ComicSaveEditScreen(
                    comic = comic,
                    comicViewModel = comicViewModel,
                    navController = navController,
                    addEditComicViewModel = addEditComicViewModel
                )
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