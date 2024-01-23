package data.networksource.network.dto.ingredients

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IngredientDTO(
    @SerialName("strIngredient1")
    val strIngredient1: String,
)
