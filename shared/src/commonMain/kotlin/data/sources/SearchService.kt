package data.sources

import data.network.UrlRoutes
import data.network.dto.drinkDto.DrinkDTOItem
import data.network.networkutils.BaseApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class SearchService(private val client: HttpClient) : BaseApiResponse() {
    suspend fun searchDrinkByName(searchText: String): Result<List<DrinkDTOItem>> {
        return safeApiCall {
            client.get(UrlRoutes.SearchByCockTailName.path) {
                parameter(UrlRoutes.SearchByCockTailName.queryKey!!, searchText)
            }
        }
    }

    suspend fun getDrinkDetails(id: String): Result<List<DrinkDTOItem>> {
        return safeApiCall {
            client.get(
                UrlRoutes.GetCocktailByID.path,
            ) {
                parameter(UrlRoutes.GetCocktailByID.path, id)
            }
        }
    }
}
