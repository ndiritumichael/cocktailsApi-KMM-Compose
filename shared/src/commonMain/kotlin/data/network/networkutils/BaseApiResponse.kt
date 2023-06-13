package data.network.networkutils

import io.github.aakira.napier.Napier
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.util.InternalAPI
import kotlinx.serialization.Serializable

abstract class BaseApiResponse {

    @OptIn(InternalAPI::class)
    suspend inline fun <reified T>  safeApiCall(apiCall: suspend () -> HttpResponse): Result<T> {
        return try {
            val response = apiCall()

            // val stream = String(response.content.toInputStream().readBytes(), StandardCharsets.UTF_8)

            if (response.status == HttpStatusCode.OK) {
                val data: GenericDTOHolder<T> = response.body()
                Result.success(
                    data.drinks,
                )
            } else {
                val data = response.body<String>()
                Napier.e("could not complete request ${response.content}")
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
    @Serializable val drinks: T,
)
