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
import com.example.estante.views.character.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        val characterViewModel: CharacterViewModel by viewModels<CharacterViewModel> {
            CharacterViewModelFactory(
                (this.applicationContext as BookshelfApplication).bookshelfDatabase.characterDao()
            )
        }
        val addEditCharacterViewModel: AddEditCharacterViewModel by viewModels()
        addEditCharacterViewModel.startBy(characterViewModel.getStartPoint())

        setContent {
            EstanteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BookshelfApp(
                        collectionViewModel,
                        addEditCollectionViewModel,
                        comicViewModel,
                        addEditComicViewModel,
                        characterViewModel,
                        addEditCharacterViewModel,
                    )
                }
            }
        }
    }
}

@Composable
fun BookshelfApp(
    collectionViewModel: CollectionViewModel,
    addEditCollectionViewModel: AddEditCollectionViewModel,
    comicViewModel: ComicViewModel,
    addEditComicViewModel: AddEditComicViewModel,
    characterViewModel: CharacterViewModel,
    addEditCharacterViewModel: AddEditCharacterViewModel,
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
            startDestination = Screen.CollectionScreen.route
        ) {

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

            composable(Screen.CharacterScreen.route) {
                CharacterScreen(navController, characterViewModel)
            }

            composable(
                route = "character/{characterId}",
                arguments = listOf(navArgument("characterId") {
                    defaultValue = -1
                    type = NavType.IntType
                })
            ) {
                val character = characterViewModel.getCharacter(
                    it.arguments?.getInt("characterId") ?: -1
                )
                CharacterSaveEditScreen(
                    character = character,
                    characterViewModel = characterViewModel,
                    navController = navController,
                    addEditCharacterViewModel = addEditCharacterViewModel
                )
            }

            composable(Screen.CharacterDetails.route) {

            }

        }
    }
}

private val bottomNavScreens = listOf(
    Screen.CollectionScreen,
    Screen.ComicScreen,
    Screen.CharacterScreen
)

sealed class Screen(
    val route: String,
    @DrawableRes val icon: Int,
    @StringRes val name: Int,
) {
    object CollectionScreen :
        Screen("collection", R.drawable.collection_icon, R.string.collection)

    object ComicScreen : Screen("comic", R.drawable.comic_icon, R.string.comic)

    object CharacterScreen : Screen("character", R.drawable.character_icon, R.string.character)

    object CollectionDetails :
        Screen("collection_details", R.drawable.collection_icon, R.string.collection)

    object ComicDetails : Screen("comic_details", R.drawable.comic_icon, R.string.comic)

    object CharacterDetails : Screen("character_details", R.drawable.character_icon, R.string.character)
}