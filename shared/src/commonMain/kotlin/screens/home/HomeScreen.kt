package screens.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.HomeScreenPresenter

object HomeScreen : Screen, KoinComponent {

    private val presenter: HomeScreenPresenter by inject()

    @Composable
    override fun Content() {
        val categoriesState by presenter.categories.collectAsState()

        if (categoriesState.isLoading) {
            CircularProgressIndicator()
        }

        categoriesState.errorMessage?.let { error ->
            Text(error, color = Color.Red)
        }

        if (categoriesState.categories.isNotEmpty()) {
            LazyVerticalGrid(columns = GridCells.Adaptive(175.dp), modifier = Modifier.fillMaxWidth()) {
                items(categoriesState.categories) { category ->
                    CategoryCard(category, colorList.random()) {
                        println("The category is $category")
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
