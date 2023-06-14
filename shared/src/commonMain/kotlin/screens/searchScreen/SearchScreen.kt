package screens.searchScreen

import AsyncImage
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.koin.core.component.get
import presentation.DrinksSearchPresenter

@Composable
fun SearchScreen(presenter: DrinksSearchPresenter) {
    //   var searchtext /*by remember {
    // mutableStateOf("")
    // }*/
    val searchtext = presenter.searchText.collectAsState().value
    val searchUiState = presenter.searchState.collectAsState().value

    Scaffold(topBar = {
        CustomSearchBar(value = searchtext, placeholder = "Search Cocktails", navigateUp = {}, onValueChange = {
            // searchtext = it
            presenter.changeSearchString(it)
            presenter.searchDrinks(it)
        })
    }) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyVerticalGrid(columns = GridCells.Fixed(2), contentPadding = PaddingValues(4.dp)) {
                items(searchUiState.data) {
                    Card(modifier = Modifier.padding(4.dp)) {
                        Box() {
                            AsyncImage(it.drinkImage, modifier = Modifier, loadingPlaceHolder = {
                                Box(modifier = Modifier.background(Color.LightGray).size(200.dp)) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.align(Alignment.Center),
                                        color = Color.Magenta,
                                    )
                                }
                            })

                            Text(it.name, modifier = Modifier.align(Alignment.BottomCenter), color = Color.White, fontWeight = FontWeight.Bold)
                            Box(
                                modifier = Modifier.fillMaxSize().background(
                                    color = Color.Black,
                                ).size(100.dp).co,

                            )
                        }
                    }
                }
            }
            searchUiState.errorMessage?.let {
                Text(it, color = Color.Red)
            }

            AnimatedVisibility(searchUiState.isLoading, modifier = Modifier.align(Alignment.Center)) {
                CircularProgressIndicator(modifier = Modifier.size(50.dp).align(Alignment.Center))
            }
        }
    }
}
