package domain.sources

import data.networksource.SearchService
import domain.models.DrinkDetailModel
import domain.models.DrinkModel
import domain.models.mapper.toDrinkDetailModel
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

    override suspend fun getDrinkDetails(id: String): Result<DrinkDetailModel> {
        return api.getDrinkDetails(id).toModelResult {
            it.first().toDrinkDetailModel()
        }
    }
}
