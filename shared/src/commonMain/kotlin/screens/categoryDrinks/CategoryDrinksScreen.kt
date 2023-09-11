package screens.categoryDrinks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.HomeScreenPresenter

class CategoryDrinksScreen(val categoryName: String) : Screen, KoinComponent {

    private val presenter: HomeScreenPresenter by inject()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val categoryDrinkState by presenter.categoryDrinkState.collectAsState()
        LaunchedEffect(true) {
            presenter.getDrinksInCategory(categoryName)
        }
        Scaffold(modifier = Modifier.fillMaxSize(), topBar = { CenterAlignedTopAppBar({ Text("$categoryName Cocktails") }) }) {
            Box(modifier = Modifier.fillMaxWidth().padding(it)) {
            }
        }
    }
}
