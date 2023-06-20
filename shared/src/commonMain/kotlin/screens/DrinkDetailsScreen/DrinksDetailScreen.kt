package screens.DrinkDetailsScreen

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DrinksSearchPresenter

data class DrinksDetailScreen(val drinkId: String) : Screen, KoinComponent {
    val presenter: DrinksSearchPresenter by inject()


    @Composable
    override fun Content() {

        LaunchedEffect(true){
            presenter.
        }
Scaffold {



}
    }
}
