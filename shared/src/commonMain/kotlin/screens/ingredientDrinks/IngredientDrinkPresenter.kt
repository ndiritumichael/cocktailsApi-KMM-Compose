package screens.ingredientDrinks

import domain.models.DrinkModel

class IngredientDrinkPresenter {
}

sealed class  IngredientDrinkState {
     object  Idle
    object Loading

    data class Success(val drinks: DrinkModel)

    data class Failure(val errorMessage : String)


}