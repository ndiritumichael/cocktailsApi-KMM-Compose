package screens.searchScreen

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
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
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

        Scaffold(
            topBar = {
                CustomSearchBar(value = searchtext, placeholder = "Search Cocktails", navigateUp = {
                    navigator.pop()
                }, onValueChange = {
                    // searchtext = it
                    presenter.changeSearchString(it)
                })
            },
            modifier = Modifier.nestedScroll(scrollBehaviour.nestedScrollConnection),
        ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                CocktailListGrid(drinks = searchUiState.data) { index, drink ->
                    navigator.push(DrinksDetailScreen(drink))
                }

                searchUiState.errorMessage?.let {
                    Text(it, color = Color.Red)
                }

                AnimatedVisibility(
                    searchUiState.isLoading,
                    modifier = Modifier.align(Alignment.TopCenter),
                ) {
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
fun CocktailListGrid(
    modifier: Modifier = Modifier,
    drinks: List<DrinkModel>,
    gridWidthItems: Int = 2,
    onClick: (index: Int, id: String) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(gridWidthItems),
        contentPadding = PaddingValues(4.dp),
    ) {
        itemsIndexed(drinks) { index, drink ->
            CockTailCard(drink) {
                onClick(index, drink.id)
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
            KamelImage(
                asyncPainterResource(drink.drinkImage),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
                onLoading = { progress ->

                    Card(modifier = Modifier.height(400.dp).fillMaxWidth().padding(8.dp)) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(
                                progress = progress,
                                modifier = Modifier.align(Alignment.Center),
                                color = Color.Magenta,
                            )
                        }
                    }
//                    Box(
//                        modifier = Modifier.background(Color.LightGray).height(imageHeight)
//                            .fillMaxWidth(),
//                    ) {
//                        CircularProgressIndicator(
//                            progress = progress,
//                            modifier = Modifier.align(Alignment.Center),
//                            color = Color.Magenta,
//                        )
//                    }
                },
                contentDescription = null,
            )

            Box(
                modifier = Modifier.height(imageHeight).fillMaxWidth().background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Color.Black.copy(0.5f),
                            Color.Black,
                        ),
                    ),
                ).align(Alignment.BottomCenter),

            ) {
                Text(
                    drink.name,
                    modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth()
                        .padding(bottom = 10.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
