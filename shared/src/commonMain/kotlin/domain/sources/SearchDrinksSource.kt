package domain.sources

import domain.models.DrinkDetailModel
import domain.models.DrinkModel

interface SearchDrinksSource {

    suspend fun searchDrinkByName(name: String): Result<List<DrinkModel>>

    suspend fun getDrinkDetails(id: String): Result<DrinkDetailModel>
}

interface HomeScreenSource {

    suspend fun getCockTailoftheDay(): Result<DrinkModel>

    suspend fun getCocktailCategories(): Result<List<String>>
    
    suspend fun getcategoryDrinks(category: String): Result<List<DrinkModel>>

    suspend fun getAllIngredientsList(): Result<List<String>>

    suspend fun getIngredientDrinks(ingredient: String): Result<List<DrinkModel>>
}
