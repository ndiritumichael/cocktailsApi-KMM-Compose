package screens.searchScreen

import AsyncImage
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import presentation.DrinksSearchPresenter

    @Composable
    fun SearchScreen(presenter: DrinksSearchPresenter = DrinksSearchPresenter()) {
        var searchtext by remember {
            mutableStateOf("")
        } // presenter.searchText.collectAsState().value
        val searchUiState = presenter.searchState.collectAsState().value
        Scaffold(topBar = {
            CustomSearchBar(value = searchtext, placeholder = "Search Cocktails", navigateUp = {}, onValueChange = {
                searchtext = it
                presenter.searchDrinks(it)
            })
        }) {
            Box() {
                LazyColumn {
                    items(searchUiState.data) {
                        Column {
                            AsyncImage(it.drinkImage)
                            Text(it.name)
                        }
                    }
                }
                searchUiState.errorMessage?.let {
                    Text(it, color = Color.Red)
                }

                AnimatedVisibility (searchUiState.isLoading){

                    CircularProgressIndicator(modifier = Modifier.size(50.dp).align(Alignment.Center))
                }
            }
        }
    }

