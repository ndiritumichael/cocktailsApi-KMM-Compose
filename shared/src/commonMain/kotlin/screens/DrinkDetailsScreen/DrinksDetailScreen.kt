package screens.DrinkDetailsScreen

import cafe.adriel.voyager.core.screen.Screen
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class DrinksDetailScreen(val drinkId: String) : Screen, KoinComponent {
    val presenter: DrinksDetailScreen by inject()

    override fun Content() {

    }
}
