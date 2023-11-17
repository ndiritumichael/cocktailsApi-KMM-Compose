package data.networksource

import data.network.UrlRoutes
import data.network.dto.drinkDto.CategoryDrinkDTO
import data.network.dto.drinkDto.CategoryDto
import data.network.dto.drinkDto.DrinkDTOItem
import data.network.dto.ingredients.IngredientDTO
import data.network.dto.ingredients.IngredientDetailDTO
import data.network.networkutils.BaseApiResponse
import data.network.networkutils.GenericDrinkDTOHolder
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class CocktailService(private val client: HttpClient) : BaseApiResponse() {

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

    suspend fun getIngredientList(): Result<List<IngredientDTO>> {
        return safeApiCall {
            client.get(UrlRoutes.ListIngredients.path)
        }
    }

    suspend fun getIngredientData(name: String): IngredientDetailDTO {
        return client.get(UrlRoutes.GetDrinkbyIngredient.path) {
            parameter(UrlRoutes.GetDrinkbyIngredient.queryKey!!, name)
        }.body()
    }

    suspend fun searchDrinkbyIngredient(name: String): GenericDrinkDTOHolder<List<CategoryDrinkDTO>> {
        with(UrlRoutes.FilterbyIngredient) {
            return client.get(path) {
                parameter(queryKey!!, name)
            }.body()
        }
    }
}
