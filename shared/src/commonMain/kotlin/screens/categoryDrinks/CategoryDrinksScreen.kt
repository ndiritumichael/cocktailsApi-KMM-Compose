package screens.categoryDrinks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.CategoryDrinkState
import presentation.HomeScreenPresenter
import screens.drinkDetailsScreen.DrinksDetailScreen
import screens.searchScreen.CocktailListGrid

class CategoryDrinksScreen(val categoryName: String, val categories: List<String>) : Screen, KoinComponent {

    private val presenter: HomeScreenPresenter by inject()

    private var initialIndex = categories.indexOf(categoryName)

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val categoryDrinkState by presenter.categoryDrinkState.collectAsState()
        var selectedCategoryIndex by remember {
            mutableIntStateOf(initialIndex)
        }

        val pagerState = rememberPagerState(initialPage = initialIndex) { categories.size }
        LaunchedEffect(selectedCategoryIndex) {
            pagerState.animateScrollToPage(selectedCategoryIndex)
        }
        LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
            if (pagerState.isScrollInProgress.not())selectedCategoryIndex = pagerState.currentPage
        }

        val navigator = LocalNavigator.currentOrThrow
        LaunchedEffect(selectedCategoryIndex) {
            presenter.getDrinksInCategory(categories[selectedCategoryIndex])
        }
        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
//            ScrollableTabRow(selectedCategoryIndex, modifier = Modifier.padding(start = 48.dp)) {
//                categories.forEachIndexed { index, name ->
//
//                    Tab(
//                        selected = index == selectedCategoryIndex,
//                        text = { Text(name) },
//                        onClick = {
//                            selectedCategoryIndex = index
//                        },
//
//                    )
//                }
//            }
            TopAppBar(
                navigationIcon = {
                    IconButton(

                        onClick = {
                            navigator.pop()
                        },
                    ) {
                        Icon(Icons.Default.ArrowBack, "null")
                    }
                }, 
                title = {},
                actions = {
                    ScrollableTabRow(selectedCategoryIndex, modifier = Modifier.padding(start = 48.dp)) {
                        categories.forEachIndexed { index, name ->

                            Tab(
                                selected = index == selectedCategoryIndex,
                                text = { Text(name) },
                                onClick = {
                                    selectedCategoryIndex = index
                                },

                            )
                        }
                    }
                },

            )
//            CenterAlignedTopAppBar({ Text("$categoryName Cocktails") }, navigationIcon = {
//                IconButton({ navigator.pop() }) {
//                    Icon(imageVector = Icons.Default.ArrowBack, "go back")
//                }
//            })
        }) { paddingValues ->
            HorizontalPager(pagerState, modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.fillMaxWidth().padding(paddingValues)) {
                    when (val state = categoryDrinkState) {
                        is CategoryDrinkState.Failure -> {
                            Column {
                                Text(state.errorMessage)
                                Button({ presenter.getDrinksInCategory(categoryName) }) {
                                    Text("retry")
                                }
                            }
                        }

                        CategoryDrinkState.Idle -> {
                        }

                        CategoryDrinkState.Loading -> CircularProgressIndicator(
                            modifier = Modifier.align(
                                Alignment.Center,
                            ),
                        )

                        is CategoryDrinkState.Success -> {
                            CocktailListGrid(drinks = state.drinks) { index, drink ->
                                initialIndex = index
                                navigator.push(DrinksDetailScreen(drink))
                            }
                        }
                    }
                }
            }
        }
    }
}
