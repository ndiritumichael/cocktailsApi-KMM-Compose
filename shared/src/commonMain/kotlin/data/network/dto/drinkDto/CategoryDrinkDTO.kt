package data.network.dto.drinkDto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDrinkDTO(
    @SerialName("idDrink")
    val idDrink: String,
    @SerialName("strDrink")
    val strDrink: String,
    @SerialName("strDrinkThumb")
    val strDrinkThumb: String
)