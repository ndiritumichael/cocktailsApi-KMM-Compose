package screens.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.NavigationRail
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.HomeScreenPresenter
import screens.searchScreen.SearchScreen

object HomeScreen : Screen, KoinComponent {

    private val presenter: HomeScreenPresenter by inject()

    @Composable
    override fun Content() {
        val categoriesState by presenter.categories.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(topBar = {
            TopAppBar(title = { Text("Kakashi Cocktails") }, actions = {
                IconButton(onClick = {
                    navigator.push(SearchScreen)
                }){
                    Icon(Icons.Default.Search,"search icon")
                }
            })
        }) {
            if (categoriesState.isLoading) {
                CircularProgressIndicator()
            }

            categoriesState.errorMessage?.let { error ->
                Text(error, color = Color.Red)
            }

            if (categoriesState.categories.isNotEmpty()) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(175.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    items(categoriesState.categories) { category ->
                        CategoryCard(category, colorList.random()) {
                            println("The category is $category")
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
