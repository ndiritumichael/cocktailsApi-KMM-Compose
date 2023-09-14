package screens.ingredientDrinks

class IngredientDrinkPresenter {
}

sealed class  IngredientDrinkState {
     object  Idle
    object Loading

    data class Success(val drinks)

    data class Failure(val errorMessage : String)


}