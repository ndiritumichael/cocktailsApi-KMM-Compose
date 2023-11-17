package domain.sources

import domain.models.IngredientsModel

interface IngredientsSource {
    suspend fun getIngredientDetails(id: Int, name: String): Result<IngredientsModel>
}
