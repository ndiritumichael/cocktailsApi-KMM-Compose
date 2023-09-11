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
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.models.DrinkModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DrinksSearchPresenter
import screens.drinkDetailsScreen.DrinksDetailScreen

object SearchScreen : Screen, KoinComponent {
    private val presenter: DrinksSearchPresenter by inject()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SearchUI() {
        val searchtext = presenter.searchText.collectAsState().value
        val searchUiState = presenter.searchState.collectAsState().value
        val navigator = LocalNavigator.currentOrThrow
        val scrollBehaviour = TopAppBarDefaults.pinnedScrollBehavior()

        Scaffold(topBar = {
            CustomSearchBar(value = searchtext, placeholder = "Search Cocktails", navigateUp = {
                navigator.pop()
            }, onValueChange = {
                // searchtext = it
                presenter.changeSearchString(it)
            })
        }, modifier = Modifier.nestedScroll(scrollBehaviour.nestedScrollConnection)) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {

                CocktailListGrid(searchUiState.data){
                    navigator.push(DrinksDetailScreen(it))

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

@Composable
fun CocktailListGrid(drinks : List<DrinkModel>,gridWidthItems : Int = 2, onClick: (id : String) -> Unit){

    LazyVerticalGrid(columns = GridCells.Fixed(gridWidthItems), contentPadding = PaddingValues(4.dp)) {
        items(drinks) {
            CockTailCard(it) {
                onClick(it.id)
            }
        }
    }
}

@Composable
fun CockTailCard(drink: DrinkModel, imageHeight: Dp = 200.dp, onClick: () -> Unit) {
    Card(
        modifier = Modifier.padding(8.dp).clickable {
            onClick()
        }.fillMaxWidth().height(imageHeight),
    ) {
        Box() {
            AsyncImage(drink.drinkImage, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillBounds, loadingPlaceHolder = {
                Box(modifier = Modifier.background(Color.LightGray).height(imageHeight)) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Magenta,
                    )
                }
            })

            Box(
                modifier = Modifier.height(imageHeight).fillMaxWidth().background(
                    brush = Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(0.5f), Color.Black)),
                ).align(Alignment.BottomCenter),

            ) {
                Text(
                    drink.name,
                    modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().padding(bottom = 10.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
