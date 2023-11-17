package domain.sources

import data.networksource.CocktailService
import domain.models.DrinkModel
import domain.models.mapper.toDrinkModel
import utils.toModelResult

class HomeDrinksRepository(private val api: CocktailService) : HomeScreenSource {
    override suspend fun getCockTailoftheDay(): Result<DrinkModel> {
        return api.getRandomCockTail().toModelResult {
            it.first().toDrinkModel()
        }
    }

    override suspend fun getCocktailCategories(): Result<List<String>> {
        return api.categoryList().toModelResult {
            it.map { category ->
                category.strCategory
            }
        }
    }

    override suspend fun getcategoryDrinks(category: String): Result<List<DrinkModel>> {
        return api.getCategoryDrinks(category).toModelResult {
            it.map { drinkDTOItem ->
                drinkDTOItem.toDrinkModel()
            }
        }
    }

    override suspend fun getAllIngredientsList(): Result<List<String>> {
        return api.getIngredientList().toModelResult { ingredientModelList ->
            ingredientModelList.map { 
                it.strIngredient1
            }
        }
    }

    override suspend fun getIngredientDrinks(ingredient: String): Result<List<DrinkModel>> {
        return Result.success(emptyList())
    }
}
