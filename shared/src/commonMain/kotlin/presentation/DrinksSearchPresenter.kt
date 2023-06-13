package presentation

import domain.models.DrinkModel
import domain.sources.SearchDrinksSource
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DrinksSearchPresenter : KoinComponent {
    private val repository: SearchDrinksSource by inject()
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    private var job: Job? = null

    private val _searchText = MutableStateFlow("")
    val searchText
        get() = _searchText.asStateFlow()

    private val _searchState: MutableStateFlow<SearchScreenState> = MutableStateFlow(
        SearchScreenState(),
    )
    val searchState: StateFlow<SearchScreenState>
        get() = _searchState.asStateFlow()

    init {
        Napier.e {
            "Starting the presenter......"
        }
    }

    fun searchDrinks(search: String) {
        _searchState.value = SearchScreenState(isLoading = true)
        job?.cancel()
        job = coroutineScope.launch {
//            delay(2000)
//            val data = repository.searchDrinkByName(search)
//            data.onSuccess {
//                _searchState.value = SearchScreenState(data = it)
//
//            }.onFailure {
//                _searchState.value = SearchScreenState(errorMessage = it.message)
//            }
        }
    }

    fun changeSearchString(search: String){
        _searchText.value = search
       // searchDrinks(_searchText.value)
    }
}
data class SearchScreenState(
    val isLoading: Boolean = false,
    val data: List<DrinkModel> = emptyList(),
    val errorMessage: String? = null,
)
