package domain.repository

import data.networksource.CocktailService
import domain.models.IngredientsModel
import domain.models.mapper.toDrinkModel
import domain.sources.IngredientsSource

class IngredientsSourceImpl(private val api: CocktailService) : IngredientsSource {

    override suspend fun getIngredientDetails(id: Int, name: String): Result<IngredientsModel> {
        // get ingredient detail
        return try {
            val ingredient = api.getIngredientData(name)

            // ingredient drinks
            val drinks = api.searchDrinkbyIngredient(name).drinks ?: emptyList()

            Result.success(
                IngredientsModel(
                    name = ingredient.ingredients.first().strIngredient,
                    description = ingredient.ingredients.first().strDescription ?: "No description found",
                    drinksList = drinks.map { it.toDrinkModel() }.sortedBy { it.name },
                ),

            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
