package presentation

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
import org.koin.core.component.get

class HomeScreenPresenter(private val repo: HomeScreenSource) : KoinComponent {

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    private var categoriesJob: Job? = null
    private var randomDrinkJob: Job? = null

    private val _categoriesState = MutableStateFlow(CategoriesState())
    val categories = _categoriesState.asStateFlow()

    private val _topDrinkState = MutableStateFlow(TodaysDrinkState())
    val topDrinkState
        get() = _topDrinkState.asStateFlow()

    init {
        fetchCockTailCategories()
    }
    fun fetchCockTailCategories() {
        _categoriesState.update {
            CategoriesState(isLoading = true)
        }
        categoriesJob?.cancel()
        categoriesJob = scope.launch {
            repo.getCocktailCategories().onSuccess { categories ->
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
