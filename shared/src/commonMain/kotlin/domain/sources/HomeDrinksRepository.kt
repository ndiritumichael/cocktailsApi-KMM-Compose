package domain.sources

import data.sources.HomeService
import domain.models.DrinkModel
import domain.models.mapper.toDrinkModel
import utils.toModelResult

class HomeDrinksRepository(private val api: HomeService) : HomeScreenSource {
    override suspend fun getCockTailoftheDay(): Result<DrinkModel> {
        return api.getRandomCockTail().toModelResult {
            it.first().toDrinkModel()
        }
    }

    override suspend fun getCocktailCategories(): Result<List<String>> {
    return api.categoryList().toModelResult {
        it.map {category ->
            category.strCategory


        }
    }
    }
}
