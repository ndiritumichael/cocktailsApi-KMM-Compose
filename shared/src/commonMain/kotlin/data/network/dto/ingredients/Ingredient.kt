package data.network.dto.ingredients

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ingredient(
    @SerialName("idIngredient")
    val idIngredient: String,
    @SerialName("strABV")
    val strABV: String?,
    @SerialName("strAlcohol")
    val strAlcohol: String,
    @SerialName("strDescription")
    val strDescription: String?,
    @SerialName("strIngredient")
    val strIngredient: String,
    @SerialName("strType")
    val strType: String?,
)
