package domain.sources

import domain.models.DrinkModel

interface SearchDrinksSource {

  suspend  fun searchDrinkByName(name: String): Result<List<DrinkModel>>
}
