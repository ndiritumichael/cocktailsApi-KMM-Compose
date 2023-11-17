package screens.ingredientDetails

import domain.models.IngredientsModel
import domain.sources.IngredientsSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import utils.getMessage

class IngredientDrinkPresenter(private val repository: IngredientsSource) {
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    private var job: Job? = null
    private val _ingredientDrinkState = MutableStateFlow<IngredientDrinkState>(IngredientDrinkState.Idle)
    val ingredientDrinkState 
        get() = _ingredientDrinkState.asStateFlow()

    fun getIngredientDetails(id: Int, name: String) {
        _ingredientDrinkState.value = IngredientDrinkState.Loading
        job?.cancel()
        job = coroutineScope.launch { 
            val data = repository.getIngredientDetails(id, name)
            
            data.onSuccess {
                _ingredientDrinkState.value = IngredientDrinkState.Success(it)
            }.onFailure {
                _ingredientDrinkState.value = IngredientDrinkState.Failure(it.getMessage())
            }
        }
    }
}

sealed class IngredientDrinkState {
    object Idle : IngredientDrinkState()
    object Loading : IngredientDrinkState()

    data class Success(val drinks: IngredientsModel) : IngredientDrinkState()

    data class Failure(val errorMessage: String) : IngredientDrinkState()
}
