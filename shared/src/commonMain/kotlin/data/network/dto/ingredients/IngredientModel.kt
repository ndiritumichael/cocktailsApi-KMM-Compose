package data.network.dto.ingredients


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IngredientModel(
    @SerialName("strIngredient1")
    val strIngredient1: String
)