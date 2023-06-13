package domain.models.mapper

import data.network.dto.drinkDto.DrinkDTOItem
import domain.models.DrinkModel

fun DrinkDTOItem.toDrinkModel(): DrinkModel {
    return DrinkModel(
        id = idDrink,
        name = strDrink,
        isAlcoholic = strAlcoholic == "Alcoholic",
        drinkImage = strDrinkThumb,

    )
}
