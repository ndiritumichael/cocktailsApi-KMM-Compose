package di

import data.network.apiClient
import data.sources.SearchService
import domain.sources.SearchDrinksRepository
import domain.sources.SearchDrinksSource
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.core.context.startKoin
import org.koin.dsl.module
import presentation.DrinksSearchPresenter

fun initKoinIos() = startKoin {
    modules(commonModules)
}.also {
    Napier.base(DebugAntilog())
}

fun initLogging() = Napier.base(DebugAntilog())



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
        DrinksSearchPresenter(get())
    }
}
