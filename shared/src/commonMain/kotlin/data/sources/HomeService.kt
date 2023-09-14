package data.sources

import data.network.UrlRoutes
import data.network.dto.drinkDto.CategoryDrinkDTO
import data.network.dto.drinkDto.CategoryDto
import data.network.dto.drinkDto.DrinkDTOItem
import data.network.dto.ingredients.IngredientModel
import data.network.networkutils.BaseApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class HomeService(private val client: HttpClient) : BaseApiResponse() {

    suspend fun getRandomCockTail(): Result<List<DrinkDTOItem>> {
        return safeApiCall {
            client.get(UrlRoutes.GetRandomCocktail.path)
        }
    }

    suspend fun categoryList(): Result<List<CategoryDto>> {
        return safeApiCall {
            client.get(UrlRoutes.ListCategories.path)
        }
    }

    suspend fun getCategoryDrinks(category: String): Result<List<CategoryDrinkDTO>> {
        return safeApiCall {
            client.get(UrlRoutes.FilterbyCategory.path) {
                parameter(UrlRoutes.FilterbyCategory.queryKey!!, category)
            }
        }
    }

    suspend fun getIngredientList(): Result<List<IngredientModel>> {
        return safeApiCall {
            client.get(UrlRoutes.ListIngredients.path)
        }
    }

}
