package domain.sources

import data.sources.SearchService
import domain.models.DrinkModel
import domain.models.mapper.toDrinkModel
import utils.toModelResult

class SearchDrinksRepository(private val api: SearchService) : SearchDrinksSource {
    override suspend fun searchDrinkByName(name: String): Result<List<DrinkModel>> {
        return api.searchDrinkByName(name).toModelResult {
            it.map { drinkDTOItem ->

                drinkDTOItem.toDrinkModel()
            }
        }
    }
}
