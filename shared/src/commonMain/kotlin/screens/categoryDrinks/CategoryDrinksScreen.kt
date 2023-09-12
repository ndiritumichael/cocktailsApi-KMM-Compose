package screens.categoryDrinks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.CategoryDrinkState
import presentation.HomeScreenPresenter
import screens.drinkDetailsScreen.DrinksDetailScreen
import screens.searchScreen.CocktailListGrid

class CategoryDrinksScreen(val categoryName: String) : Screen, KoinComponent {

    private val presenter: HomeScreenPresenter by inject()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val categoryDrinkState by presenter.categoryDrinkState.collectAsState()

        val navigator = LocalNavigator.currentOrThrow
        LaunchedEffect(true) {
            presenter.getDrinksInCategory(categoryName)
        }
        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            CenterAlignedTopAppBar({ Text("$categoryName Cocktails") }, navigationIcon = {
                IconButton({ navigator.pop() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, "go back")
                }
            })
        }) {
            Box(modifier = Modifier.fillMaxWidth().padding(it)) {
                when (val state = categoryDrinkState) {
                    is CategoryDrinkState.Failure -> {
                        Column {
                            Text(state.errorMessage)
                            Button({ presenter.getDrinksInCategory(categoryName) }) {
                                Text("retry")
                            }
                        }
                    }
                    CategoryDrinkState.Idle -> {
                    }
                    CategoryDrinkState.Loading -> CircularProgressIndicator()
                    is CategoryDrinkState.Success -> {
                        CocktailListGrid(state.drinks) {
                            navigator.push(DrinksDetailScreen(it))
                        }
                    }
                }
            }
        }
    }
}
