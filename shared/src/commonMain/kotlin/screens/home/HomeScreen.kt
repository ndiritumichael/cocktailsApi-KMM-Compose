package screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
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
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.HomeScreenPresenter
import screens.categoryDrinks.CategoryDrinksScreen
import screens.drinkDetailsScreen.DrinksDetailScreen
import screens.searchScreen.CockTailCard
import screens.searchScreen.SearchScreen

object HomeScreen : Screen, KoinComponent {

    private val presenter: HomeScreenPresenter by inject()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val categoriesState by presenter.categories.collectAsState()
        val randomDrink by presenter.topDrinkState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        val scrollBehaviour = TopAppBarDefaults.pinnedScrollBehavior()

        LaunchedEffect(true){
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

                if (categoriesState.categories.isNotEmpty()) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(175.dp),
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                    ) {
                        randomDrink.drink?.let { drink ->
                            item(span = {
                                GridItemSpan(maxLineSpan)
                            }) {
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
                        item(span = {
                            GridItemSpan(maxLineSpan)
                        }) {
                            Text("Explore Categories", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(8.dp))
                        }
                        items(categoriesState.categories) { category ->
                            CategoryCard(category, colorList.random()) {
                                navigator.push(CategoryDrinksScreen(category))
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
