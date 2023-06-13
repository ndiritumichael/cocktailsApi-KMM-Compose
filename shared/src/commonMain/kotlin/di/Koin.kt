package di

import data.network.apiClient
import data.sources.SearchService
import domain.sources.SearchDrinksRepository
import domain.sources.SearchDrinksSource
import org.koin.core.context.startKoin
import org.koin.dsl.module
import presentation.DrinksSearchPresenter

fun initKoinIos() = startKoin {
    modules(commonModules)
}

val commonModules = module {
    single {
        apiClient()
    }
    single {
        SearchService(get())
    }
    single<SearchDrinksSource> {
        SearchDrinksRepository(get())
    }
    factory {
        DrinksSearchPresenter()
    }
}
