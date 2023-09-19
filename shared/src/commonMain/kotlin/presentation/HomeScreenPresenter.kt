package presentation

import data.network.dto.ingredients.IngredientModel
import domain.models.DrinkModel
import domain.sources.HomeScreenSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import utils.getMessage

class HomeScreenPresenter(private val homeScreenSource: HomeScreenSource) : KoinComponent {

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    private var categoriesJob: Job? = null
    private var randomDrinkJob: Job? = null
    private var drinkCategoriesJob : Job? = null
    private var ingredientsJob: Job? = null

    private val _categoriesState = MutableStateFlow(CategoriesState())
    val categories = _categoriesState.asStateFlow()

    private val _topDrinkState = MutableStateFlow(TodaysDrinkState())
    val topDrinkState
        get() = _topDrinkState.asStateFlow()

    private val _categoryDrinkState = MutableStateFlow<CategoryDrinkState>(CategoryDrinkState.Idle)
    val categoryDrinkState
        get() = _categoryDrinkState.asStateFlow()


    private val _ingredientStates = MutableStateFlow<IngredientStates>(IngredientStates.Idle)
    val ingredientStates
        get() = _ingredientStates.asStateFlow()




    fun getHomeScreenItems() {
        fetchCockTailCategories()
        fetchTodayDrink()
        fetchIngredientList()
    }

    fun fetchIngredientList(){
        ingredientsJob?.cancel()
        _ingredientStates.value = IngredientStates.Loading
        ingredientsJob = scope.launch {
            homeScreenSource.getAllIngredientsList().onSuccess {
                _ingredientStates.value = IngredientStates.Success(it)
            }.onFailure {
                _ingredientStates.value = IngredientStates.Failure(it.getMessage())
            }
        }

    }
    fun fetchTodayDrink() {
        randomDrinkJob?.cancel()
        _topDrinkState.value = TodaysDrinkState(isLoading = true)
        randomDrinkJob = scope.launch {
            homeScreenSource.getCockTailoftheDay().onSuccess {
                _topDrinkState.value = TodaysDrinkState(drink = it)
            }.onFailure {
                _topDrinkState.value = TodaysDrinkState(errorMessage = it.getMessage())
            }
        }
    }
    fun fetchCockTailCategories() {
        _categoriesState.update {
            CategoriesState(isLoading = true)
        }
        categoriesJob?.cancel()
        categoriesJob = scope.launch {
            homeScreenSource.getCocktailCategories().onSuccess { categories ->
                _categoriesState.update {
                    CategoriesState(categories = categories)
                }
            }.onFailure { error ->
                _categoriesState.update {
                    CategoriesState(errorMessage = error.message)
                }
            }
        }
    }

    fun getDrinksInCategory(category: String){
        _categoryDrinkState.value = CategoryDrinkState.Loading
        drinkCategoriesJob?.cancel()
        drinkCategoriesJob = scope.launch {
            homeScreenSource.getcategoryDrinks(category).onSuccess {
                _categoryDrinkState.value = CategoryDrinkState.Success(it)
            }.onFailure {
                _categoryDrinkState.value = CategoryDrinkState.Failure(it.getMessage())
            }
        }
    }


}

data class CategoriesState(
    val isLoading: Boolean = false,
    val categories: List<String> = emptyList(),
    val errorMessage: String? = null,
)

data class TodaysDrinkState(
    val isLoading: Boolean = false,
    val drink: DrinkModel? = null,
    val errorMessage: String? = null,
)

sealed class CategoryDrinkState {
    object Loading : CategoryDrinkState()
    object Idle : CategoryDrinkState()

    data class Success (val drinks : List<DrinkModel>) : CategoryDrinkState()

    data class Failure (val errorMessage: String) : CategoryDrinkState()

}

sealed class IngredientStates{
    object Loading : IngredientStates()
    object Idle : IngredientStates()

    data class Success (val ingredients : List<String>) : IngredientStates()

    data class Failure (val errorMessage: String) : IngredientStates()
}