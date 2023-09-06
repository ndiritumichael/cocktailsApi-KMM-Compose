package data.network.networkutils

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

abstract class BaseApiResponse {

    // @OptIn(InternalAPI::class)
    suspend inline fun <reified T> safeApiCall(apiCall: () -> HttpResponse): Result<T> {
        return try {
            val response = apiCall()
            if (response.status == HttpStatusCode.OK) {
                val data: GenericDTOHolder<T> = response.body()
                if (data.drinks != null) {
                    Result.success(data.drinks)
                } else {
                    Result.failure(Exception("Could Not Find any Drinks"))
                }
            } else {
                val data = response.body<String>()
// TODO: Handle errors better
                Result.failure(Exception(" Something went Wrong : \n $data"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(Exception(" Something went Wrong : ${e.message}"))
        }
    }
}

@Serializable
data class GenericDTOHolder<T>(
    @Serializable val drinks: T?,
)
