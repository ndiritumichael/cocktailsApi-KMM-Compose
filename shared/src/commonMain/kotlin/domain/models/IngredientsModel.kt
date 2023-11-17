package domain.models

data class IngredientsModel(
    val name: String,
    val description: String,
    val drinksList: List<DrinkModel>,
)
