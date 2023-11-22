package presentation

import domain.models.DrinkDetailModel
import domain.models.DrinkModel
import domain.sources.SearchDrinksSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class DrinksSearchPresenter(private val repository: SearchDrinksSource) : KoinComponent {

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    private var job: Job? = null
    private var detailJob: Job? = null

    private val _searchText = MutableStateFlow("")
    val searchText
        get() = _searchText.asStateFlow()

    private val _searchState: MutableStateFlow<SearchScreenState> = MutableStateFlow(
        SearchScreenState(),
    )
    val searchState: StateFlow<SearchScreenState>
        get() = _searchState.asStateFlow()

    private val _drinkDetailState = MutableStateFlow(DetailScreenState())
    val drinkDetailState
        get() = _drinkDetailState.asStateFlow()

    fun getDrinkDetails(id: String) {
        _drinkDetailState.value = DetailScreenState(isLoading = true)
        detailJob?.cancel()
        detailJob = coroutineScope.launch {
            val data = repository.getDrinkDetails(id)
            data.onSuccess {
                _drinkDetailState.value = DetailScreenState(data = it)
            }.onFailure {
                _drinkDetailState.value = DetailScreenState(errorMessage = it.message)
            }
        }
    }

    fun searchDrinks(search: String) {
        _searchState.value = SearchScreenState(isLoading = true)
        job?.cancel()

        if (search.length < 3) {
            SearchScreenState(isLoading = false)
        } else {
            job = coroutineScope.launch {
                delay(500)
                val data = repository.searchDrinkByName(search)

                data.onSuccess {
                    _searchState.value = SearchScreenState(data = it)
                }.onFailure {
                    _searchState.value = SearchScreenState(errorMessage = it.message)
                }
            }
        }
    }

    fun changeSearchString(search: String) {
        _searchText.value = search
        searchDrinks(search)
        // searchDrinks(_searchText.value)
    }
}

data class SearchScreenState(

    val isLoading: Boolean = false,
    val data: List<DrinkModel> = emptyList(),

    val errorMessage: String? = null,
)
data class DetailScreenState(
    val isLoading: Boolean = false,
    val data: DrinkDetailModel? = null,
    val errorMessage: String? = null,
)
