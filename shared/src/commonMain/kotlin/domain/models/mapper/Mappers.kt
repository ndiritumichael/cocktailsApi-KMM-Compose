package domain.models.mapper

import data.network.dto.drinkDto.DrinkDTOItem
import domain.models.DrinkDetailModel
import domain.models.DrinkIngredientsModel
import domain.models.DrinkInstructionLanguages
import domain.models.DrinkModel

fun DrinkDTOItem.toDrinkModel(): DrinkModel {
    return DrinkModel(
        id = idDrink,
        name = strDrink,
        isAlcoholic = strAlcoholic == "Alcoholic",
        drinkImage = strDrinkThumb,

    )
}

fun DrinkDTOItem.toDrinkDetailModel(): DrinkDetailModel {
    val ingredientsModel: List<DrinkIngredientsModel> = buildList {
        if (strIngredient1 != null && strMeasure1 != null) { add(DrinkIngredientsModel(strIngredient1, strMeasure1)) }
        if (strIngredient2 != null && strMeasure2 != null) { add(DrinkIngredientsModel(strIngredient2, strMeasure2)) }
        if (strIngredient3 != null && strMeasure3 != null) { add(DrinkIngredientsModel(strIngredient3, strMeasure3)) }
        if (strIngredient4 != null && strMeasure4 != null) { add(DrinkIngredientsModel(strIngredient4, strMeasure4)) }
        if (strIngredient5 != null && strMeasure5 != null) { add(DrinkIngredientsModel(strIngredient5, strMeasure5)) }
        if (strIngredient6 != null && strMeasure6 != null) { add(DrinkIngredientsModel(strIngredient6, strMeasure6)) }
        if (strIngredient7 != null && strMeasure7 != null) { add(DrinkIngredientsModel(strIngredient7, strMeasure7)) }
        if (strIngredient8 != null && strMeasure8 != null) { add(DrinkIngredientsModel(strIngredient8, strMeasure8)) }
        if (strIngredient9 != null && strMeasure9 != null) { add(DrinkIngredientsModel(strIngredient9, strMeasure9)) }
        if (strIngredient10 != null && strMeasure10 != null) { add(DrinkIngredientsModel(strIngredient10, strMeasure10)) }
        if (strIngredient11 != null && strMeasure11 != null) { add(DrinkIngredientsModel(strIngredient11, strMeasure11)) }
        if (strIngredient12 != null && strMeasure12 != null) { add(DrinkIngredientsModel(strIngredient12, strMeasure12)) }
        if (strIngredient13 != null && strMeasure13 != null) { add(DrinkIngredientsModel(strIngredient13, strMeasure13)) }
        if (strIngredient14 != null && strMeasure14 != null) { add(DrinkIngredientsModel(strIngredient14, strMeasure14)) }
        if (strIngredient15 != null && strMeasure15 != null) { add(DrinkIngredientsModel(strIngredient15, strMeasure15)) }
    }
    val instructions = buildList {
        add(DrinkInstructionLanguages("EN", strInstructions))
        strInstructionsDE?.let { add(DrinkInstructionLanguages("DE", it)) }
        strInstructionsES?.let { add(DrinkInstructionLanguages("ES", it)) }
        strInstructionsFR?.let { add(DrinkInstructionLanguages("FR", it)) }
        strInstructionsIT?.let { add(DrinkInstructionLanguages("IT", it)) }
        strInstructionsZHHANS?.let { add(DrinkInstructionLanguages("ZH-HANS", it)) }
        strInstructionsZHHANT?.let { add(DrinkInstructionLanguages("ZH-HANT", it)) }
    }
    return DrinkDetailModel(
        id = idDrink,
        name = strDrink,
        isAlcoholic = strAlcoholic == "Alcoholic",
        drinkImage = strDrinkThumb,
        ingredient = ingredientsModel,
        instructions = instructions,

    )
}
