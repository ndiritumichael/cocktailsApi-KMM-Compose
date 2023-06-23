package domain.sources

import domain.models.DrinkDetailModel
import domain.models.DrinkModel

interface SearchDrinksSource {

    suspend fun searchDrinkByName(name: String): Result<List<DrinkModel>>

    suspend fun getDrinkDetails(id: String): Result<DrinkDetailModel>
}
