package screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.HomeScreenPresenter
import presentation.IngredientStates
import screens.categoryDrinks.CategoryDrinksScreen
import screens.drinkDetailsScreen.DrinksDetailScreen
import screens.searchScreen.CockTailCard
import screens.searchScreen.SearchScreen
import screens.uiutils.getIngredientImage

object HomeScreen : Screen, KoinComponent {

    private val presenter: HomeScreenPresenter by inject()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val categoriesState by presenter.categories.collectAsState()
        val randomDrink by presenter.topDrinkState.collectAsState()
        val ingredients by presenter.ingredientStates.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()

        LaunchedEffect(true) {
            presenter.getHomeScreenItems()
        }
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehaviour.nestedScrollConnection),
            topBar = {
                TopAppBar(title = { Text("Kakashi Cocktails") }, actions = {
                    IconButton(onClick = {
                        navigator.push(SearchScreen)
                    }) {
                        Icon(Icons.Default.Search, "search icon")
                    }
                }, scrollBehavior = scrollBehaviour)
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { presenter.getHomeScreenItems() }) {
                    Icon(Icons.Default.Refresh, "refresh")
                }
            },
        ) {
            Box(modifier = Modifier.padding(it).fillMaxSize()) {
                if (categoriesState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.TopCenter))
                }

                categoriesState.errorMessage?.let { error ->
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(error, color = Color.Red)
                        Button({
                            presenter
                                .fetchCockTailCategories()
                        }) {
                            Text("Retry")
                        }
                    }
                }

                LazyColumn(

                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                ) {
                    randomDrink.drink?.let { drink ->
                        item {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                CockTailCard(drink, imageHeight = 400.dp) {
                                    navigator.push(DrinksDetailScreen(drink.id))
                                }
                                Text(
                                    "Cocktail of the Day",
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.padding(8.dp).align(
                                        Alignment.TopCenter,
                                    ),
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                )
                            }
                        }
                    }

                    if (categoriesState.categories.isNotEmpty()) {
                        item {
                            Text("Explore Categories", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(8.dp))
                        }
                        item {
                            LazyHorizontalGrid(
                                rows = GridCells.Fixed(2),
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp).height(360.dp),
                            ) {
                                items(categoriesState.categories) { category ->
                                    CategoryCard(category, colorList.random()) {
                                        navigator.push(CategoryDrinksScreen(category))
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Text("Explore Ingredients", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(8.dp))
                    }

                    when (val ingredients = ingredients) {
                        is IngredientStates.Failure -> {
                        }
                        IngredientStates.Idle -> {}
                        IngredientStates.Loading -> {
                        }
                        is IngredientStates.Success -> {
                            item {
                                LazyHorizontalGrid(
                                    rows = GridCells.Fixed(2),
                                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp).height(250.dp),
                                ) {
                                    items(ingredients.ingredients) { name ->
                                        Card(modifier = Modifier.height(120.dp)) {
                                            Column(modifier = Modifier.padding(4.dp)) {
                                                KamelImage(
                                                    asyncPainterResource(
                                                        getIngredientImage(name),
                                                    ),
                                                    "ingredient image",
                                                )

                                                Text(name)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

val colorList = listOf(

    Color.Red,
    Color.Green,
    Color.Magenta,
    Color.Blue,

)
