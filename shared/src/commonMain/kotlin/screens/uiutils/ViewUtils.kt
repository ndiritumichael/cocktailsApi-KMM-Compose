package screens.uiutils

fun getIngredientImage(name: String): String {
    return "https://www.thecocktaildb.com/images/ingredients/$name-Small.png"
}

fun getLargeIngredientImage(name: String): String {
    return "https://www.thecocktaildb.com/images/ingredients/$name-Medium.png"
}
