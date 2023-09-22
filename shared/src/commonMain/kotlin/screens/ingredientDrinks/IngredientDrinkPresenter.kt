package screens.ingredientDrinks

import domain.models.DrinkModel

class IngredientDrinkPresenter()

sealed class IngredientDrinkState {
    object Idle : IngredientDrinkState()
    object Loading : IngredientDrinkState()

    data class Success(val drinks: DrinkModel) : IngredientDrinkState()

    data class Failure(val errorMessage: String) : IngredientDrinkState()
}
