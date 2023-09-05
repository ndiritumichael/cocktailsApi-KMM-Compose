package data.sources

import data.network.UrlRoutes
import data.network.dto.drinkDto.CategoryDto
import data.network.dto.drinkDto.DrinkDTOItem
import data.network.networkutils.BaseApiResponse
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class HomeService(private val client: HttpClient) : BaseApiResponse() {

    suspend fun getRandomCockTail(): Result<List<DrinkDTOItem>> {
        return safeApiCall{
            client.get(UrlRoutes.GetRandomCocktail.path)
        }
    }

    suspend fun categoryList(): Result<List<CategoryDto>> {
        return safeApiCall<List<CategoryDto>> {
            client.get(UrlRoutes.ListCategories.path)
        }.also {
            Napier.e {
                "The categories are $it"
            }
        }
    }
}
