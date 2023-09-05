package di

import data.network.apiClient
import data.sources.HomeService
import data.sources.SearchService
import domain.sources.HomeDrinksRepository
import domain.sources.HomeScreenSource
import domain.sources.SearchDrinksRepository
import domain.sources.SearchDrinksSource
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module
import presentation.DrinksSearchPresenter
import presentation.HomeScreenPresenter

fun initKoin(enableNetworkLogs: Boolean) = startKoin {
    modules(commonModules)
}.also {
    if (enableNetworkLogs) Napier.base(DebugAntilog())
}

fun initLogging() = Napier.base(DebugAntilog())

fun KoinApplication.Companion.start(enableNetworkLogs: Boolean): KoinApplication = initKoin(enableNetworkLogs = enableNetworkLogs)
val Koin.drinkPresenter: DrinksSearchPresenter
    get() = get()

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
    single {
        HomeService(get())
    }

    factory {
        HomeScreenPresenter(get())
    }

    single<HomeScreenSource> {

        HomeDrinksRepository(get())
    }

    single {
        HomeService(get())
    }
}
