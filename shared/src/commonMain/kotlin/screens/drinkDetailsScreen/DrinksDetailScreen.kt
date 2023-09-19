package screens.drinkDetailsScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.models.DrinkDetailModel
import domain.models.DrinkIngredientsModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DrinksSearchPresenter
import screens.common.tabs.CustomTab

data class DrinksDetailScreen(val drinkId: String) : Screen, KoinComponent {
    private val presenter: DrinksSearchPresenter by inject()

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val drinkState = presenter.drinkDetailState.collectAsState().value
        val navigator = LocalNavigator.currentOrThrow

        var selectedLanguageIndex by remember { mutableStateOf(0) }
        LaunchedEffect(true) {
            presenter.getDrinkDetails(drinkId)
            // presenter.
        }
        Scaffold {
            Box(
                modifier = Modifier.fillMaxSize().padding(it),
                contentAlignment = Alignment.TopCenter,
            ) {
                AnimatedVisibility(drinkState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(50.dp))
                }
                drinkState.errorMessage?.let { error ->
                    Column {
                        Text(error, color = Color.Red)
                        Button({ presenter.getDrinkDetails(drinkId) }) {
                            Text("Retry")
                        }
                    }
                }
            }
            drinkState.data?.let { drink ->

                LazyColumn {
                    item {
                        DrinkPoster(drink)
                    }
                    stickyHeader { Text("What you need", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(horizontal = 8.dp)) }
                    itemsIndexed(drink.ingredient, itemContent = { index, ingredient -> IngredientListSection(index + 1, ingredient) })
                    item {
                        Column {
                            Text("Instructions", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(horizontal = 8.dp))
//                            TabRow(selectedTabIndex = selectedLanguageIndex) {
//                                drink.instructions.mapIndexed { index, instruction ->
//                                    Tab(
//                                        selected = selectedLanguageIndex == index,
//                                        onClick = {
//                                            selectedLanguageIndex = index
//                                        },
//                                        text = { Text(instruction.language) },
//                                    )
//                                }
//                            }

                            CustomTab(
                                selectedItemIndex = selectedLanguageIndex,
                                items = drink.instructions.map { it.language },
                                onClick = { index ->
                                    selectedLanguageIndex = index
                                },
                            )

                            Text(drink.instructions[selectedLanguageIndex].instruction)
                        }
                    }
                }
            }
        }
        Box(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                IconButton(
                    { navigator.pop() },
                    modifier = Modifier.clip(CircleShape)
                        .background(Color.LightGray.copy(0.5f)),
                ) {
                    Icon(Icons.Filled.ArrowBack, null, tint = Color.Black)
                }
            }
        }
    }
}

@Composable
fun IngredientListSection(index: Int, ingredients: DrinkIngredientsModel) {
    Row(Modifier.padding(horizontal = 8.dp)) {
        Text("$index. ")
        Text(ingredients.name, fontWeight = FontWeight.Bold)
        Text("  -  ", fontWeight = FontWeight.Bold)
        Text(ingredients.measurements, fontWeight = FontWeight.Light)
    }
}

@Composable
fun DrinkPoster(drink: DrinkDetailModel) {
    val gradient = Brush.verticalGradient(listOf(Color.Transparent, Color.Transparent, Color.Black))

    Box(
        modifier = Modifier.fillMaxWidth()
            .height(400.dp),
        // .background(Color.Black)
    ) {
        KamelImage(
            asyncPainterResource(drink.drinkImage),
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
        )
        Box(
            modifier = Modifier.fillMaxSize()
                .background(gradient),
        )
        Text(drink.name, color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.BottomCenter).padding(8.dp), style = MaterialTheme.typography.bodyLarge)
    }
}
