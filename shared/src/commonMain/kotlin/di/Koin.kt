package di

import data.networksource.CocktailService
import data.networksource.SearchService
import data.networksource.network.apiClient
import domain.repository.IngredientsSourceImpl
import domain.sources.HomeDrinksRepository
import domain.sources.HomeScreenSource
import domain.sources.IngredientsSource
import domain.sources.SearchDrinksRepository
import domain.sources.SearchDrinksSource
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import presentation.DrinksSearchPresenter
import presentation.HomeScreenPresenter
import screens.ingredientDetails.IngredientDrinkPresenter

fun initKoin(enableNetworkLogs: Boolean, platformModules: Module = module { }) = startKoin {
    modules(commonModules, sourceSetModules, platformModules)
}.also {
    Napier.base(DebugAntilog())
}

fun initLogging() = Napier.base(DebugAntilog())

fun KoinApplication.Companion.start(enableNetworkLogs: Boolean): KoinApplication = initKoin(enableNetworkLogs = enableNetworkLogs)
val Koin.drinkPresenter: DrinksSearchPresenter
    get() = get()

val commonModules = module {

    factory {
        IngredientDrinkPresenter(get())
    }
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

        CocktailService(get())
    }

    factory {
        HomeScreenPresenter(get())
    }

    single<HomeScreenSource> {

        HomeDrinksRepository(get())
    }

    single {
        CocktailService(get())
    }

    single<IngredientsSource> {
        IngredientsSourceImpl(get())
    }
}
