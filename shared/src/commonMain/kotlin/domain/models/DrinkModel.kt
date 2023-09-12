package domain.models

data class DrinkModel(
    val id : String,
    val name : String,
    val isAlcoholic : Boolean= true,
    val drinkImage : String,

)
