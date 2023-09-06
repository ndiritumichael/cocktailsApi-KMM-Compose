package screens.searchScreen

import AsyncImage
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import presentation.DrinksSearchPresenter
import screens.DrinkDetailsScreen.DrinksDetailScreen

object SearchScreen : Screen, KoinComponent {
    private val presenter: DrinksSearchPresenter by inject()

    @Composable
    fun SearchUI() {
        //   var searchtext /*by remember {
        // mutableStateOf("")
        // }*/
        val searchtext = presenter.searchText.collectAsState().value
        val searchUiState = presenter.searchState.collectAsState().value
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(topBar = {
            CustomSearchBar(value = searchtext, placeholder = "Search Cocktails", navigateUp = {
                navigator.pop()
            }, onValueChange = {
                // searchtext = it
                presenter.changeSearchString(it)
                presenter.searchDrinks(it)
            })
        }) {
            Box(modifier = Modifier.fillMaxSize()) {
                LazyVerticalGrid(columns = GridCells.Fixed(2), contentPadding = PaddingValues(4.dp)) {
                    items(searchUiState.data) {
                        Card(
                            modifier = Modifier.padding(4.dp).clickable {
                                navigator.push(DrinksDetailScreen(it.id))
                            },
                        ) {
                            Box() {
                                AsyncImage(it.drinkImage, modifier = Modifier, loadingPlaceHolder = {
                                    Box(modifier = Modifier.background(Color.LightGray).size(200.dp)) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.align(Alignment.Center),
                                            color = Color.Magenta,
                                        )
                                    }
                                })

                                Box(
                                    modifier = Modifier.height(200.dp).fillMaxWidth().background(
                                        brush = Brush.verticalGradient(listOf(Color.Transparent, Color.Transparent, Color.Black.copy(0.5f))),
                                    ).align(Alignment.BottomCenter),

                                ) {
                                    Text(
                                        it.name,
                                        modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                    )
                                }
                            }
                        }
                    }
                }
                searchUiState.errorMessage?.let {
                    Text(it, color = Color.Red)
                }

                AnimatedVisibility(searchUiState.isLoading, modifier = Modifier.align(Alignment.Center)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(50.dp),
                        )

                        Text("Searching Cocktails...")
                    }
                }
            }
        }
    }

    @Composable
    override fun Content() {
        SearchUI()
    }
}
