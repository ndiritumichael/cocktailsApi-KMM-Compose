package domain.models

data class DrinkDetailModel(
    val id: String,
    val name: String,
    val isAlcoholic: Boolean,
    val drinkImage: String,
    val ingredient: List<DrinkIngredientsModel>,
    val instructions: List<DrinkInstructionLanguages>,

)

data class DrinkIngredientsModel(
    val name: String,
    val measurements: String,
)
data class DrinkInstructionLanguages(
    val language: String,
    val instruction: String,
)
