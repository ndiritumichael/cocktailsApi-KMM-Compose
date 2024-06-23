package data.networksource.network.dto.ingredients


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IngredientDetailDTO(
    @SerialName("ingredients")
    val ingredients: List<Ingredient>
)