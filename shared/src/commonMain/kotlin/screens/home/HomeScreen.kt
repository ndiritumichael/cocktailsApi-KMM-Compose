package screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kmpalette.loader.rememberNetworkLoader
import com.kmpalette.rememberDominantColorState
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.http.Url
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.HomeScreenPresenter
import presentation.IngredientStates
import screens.categoryDrinks.CategoryDrinksScreen
import screens.drinkDetailsScreen.DrinksDetailScreen
import screens.ingredientDetails.IngredientDetailScreen
import screens.searchScreen.CockTailCard
import screens.searchScreen.SearchScreen
import screens.settings.SettingsScreen
import screens.uiutils.getIngredientImage

object HomeScreen : Screen, KoinComponent {

    private val presenter: HomeScreenPresenter by inject()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val networkLoader = rememberNetworkLoader()
        val dominantColor = rememberDominantColorState(networkLoader)
        val categoriesState by presenter.categories.collectAsState()
        val randomDrink by presenter.topDrinkState.collectAsState()
        val ingredients by presenter.ingredientStates.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()

        Scaffold(
            //  modifier = Modifier.nestedScroll(scrollBehaviour.nestedScrollConnection),
            topBar = {
                TopAppBar(title = { Text("Welcome") }, actions = {
                    IconButton(onClick = {
                        navigator.push(SearchScreen)
                    }) {
                        Icon(Icons.Default.Search, "search icon")
                    }

                    IconButton(onClick = {
                        navigator.push(SettingsScreen)
                    }) {
                        Icon(Icons.Default.Settings, "Settings screen")
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
                LazyColumn(

                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                ) {
                    if (categoriesState.isLoading) {
                        item {
                            Card(modifier = Modifier.height(400.dp).fillMaxWidth().padding(8.dp)) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }

                    item {
                        categoriesState.errorMessage?.let { error ->
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(error, color = Color.Red)
                                Button({
                                    presenter
                                        .fetchCockTailCategories()
                                }) {
                                    Text("Retry")
                                }
                            }
                        }
                    }
                    randomDrink.drink?.let { drink ->
                        item {
                            Box() {
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
                                    color = dominantColor.onColor,
                                )
                            }
                        }
                    }

                    item {
                        Text(
                            "Explore Categories",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(8.dp),
                        )
                    }
                    item {
                        LazyHorizontalGrid(
                            rows = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
                                .height(360.dp),
                        ) {
                            if (categoriesState.isLoading) {
                                items(20) {
                                    LoadingCategoryCard()
                                }
                            }
                            itemsIndexed(categoriesState.categories) { index, category ->
                                val cardColor = remember {
                                    CategoryColors.getColor(index)
                                }
                                CategoryCard(category, cardColor) {
                                    navigator.push(
                                        CategoryDrinksScreen(
                                            category,
                                            categoriesState.categories,
                                        ),
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Text(
                            "Explore Ingredients",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(8.dp),
                        )
                    }

                    when (val ingredientStates = ingredients) {
                        is IngredientStates.Failure -> {
                        }

                        IngredientStates.Idle -> {}
                        IngredientStates.Loading -> {
                        }

                        is IngredientStates.Success -> {
                            item {
                                LazyHorizontalGrid(
                                    rows = GridCells.Fixed(2),
                                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
                                        .height(300.dp),
                                ) {
                                    items(ingredientStates.ingredients) { name ->
                                        Card(
                                            modifier = Modifier.height(150.dp).padding(4.dp),
                                            onClick = {
                                                navigator.push(IngredientDetailScreen(557, name))
                                            },
                                        ) {
                                            Column(
                                                modifier = Modifier.padding(8.dp).fillMaxHeight(),
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                            ) {
                                                KamelImage(

                                                    asyncPainterResource(
                                                        getIngredientImage(name),
                                                    ),
                                                    "ingredient image",
                                                    modifier = Modifier.size(100.dp),
                                                )

                                                Text(
                                                    name,
                                                    modifier = Modifier.size(100.dp),
                                                    textAlign = TextAlign.Center,
                                                )
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

        LaunchedEffect(randomDrink.drink) {
            randomDrink.drink?.let {
                dominantColor.updateFrom(Url(it.drinkImage))
            }
        }
    }
}

object CategoryColors {

    private val colorList = listOf(

        Color.Red,
        Color.Green,
        Color.Magenta,
        Color.Blue,

    )

    fun getColor(index: Int): Color {
        val colorIndex = index % 4
        return colorList[colorIndex]
    }
}
