package screens.drinkDetailsScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.models.DrinkIngredientsModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.delay
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DrinksSearchPresenter
import screens.common.tabs.CustomTab
import screens.ingredientDetails.IngredientDetailScreen
import screens.uiutils.getIngredientImage

data class DrinksDetailScreen(val drinkId: String) : Screen, KoinComponent {
    private val presenter: DrinksSearchPresenter by inject()

    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val drinkState = presenter.drinkDetailState.collectAsState().value
        val navigator = LocalNavigator.currentOrThrow

        var selectedLanguageIndex by remember { mutableStateOf(0) }
        LaunchedEffect(true) {
            presenter.getDrinkDetails(drinkId)
            // presenter.
        }
        Scaffold { paddingValues ->
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.TopCenter,
            ) {
                AnimatedVisibility(
                    drinkState.isLoading,
                    modifier = Modifier.align(Alignment.TopCenter),
                ) {
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
                        DrinkPoster(drink.drinkImage, drink.name)
                    }
                    stickyHeader {
                        Text(
                            "What you need",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(horizontal = 8.dp),

                        )
                    }
//                    itemsIndexed(
//                        drink.ingredient,
//                        itemContent = { index, ingredient ->
//                            IngredientListSection(
//                                index + 1,
//                                ingredient,
//                            )
//                        },
//                    )
                    item {
                        LazyHorizontalGrid(
                            rows = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxWidth().height(250.dp).padding(horizontal = 8.dp),
                        ) {
                            items(drink.ingredient) { ingredient ->
                                Card(modifier = Modifier.padding(4.dp), onClick = {
                                    navigator.push(IngredientDetailScreen(557, ingredient.name))
                                }) {
                                    Column(
                                        modifier = Modifier.padding(8.dp).fillMaxHeight(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center,
                                    ) {
                                        KamelImage(

                                            asyncPainterResource(
                                                getIngredientImage(ingredient.name),
                                            ),
                                            "ingredient image",
                                            modifier = Modifier.size(50.dp),
                                        )

                                        Text(
                                            ingredient.name,
                                            modifier = Modifier.width(100.dp),
                                            textAlign = TextAlign.Center,
                                            style = MaterialTheme.typography.labelLarge,
                                        )
                                        Text(
                                            ingredient.measurements,
                                            modifier = Modifier.width(100.dp),
                                            textAlign = TextAlign.Center,
                                            style = MaterialTheme.typography.labelLarge,
                                        )
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Column {
                            Text(
                                "Instructions",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(horizontal = 8.dp),
                            )
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
            Row(
                modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
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
fun DrinkPoster(imageLink: String, text: String) {
    val gradient = Brush.verticalGradient(listOf(Color.Transparent, Color.Black))
    var showLargeImage by remember {
        mutableStateOf(true)
    }

    Box(
        modifier = Modifier.fillMaxWidth()
            .height(400.dp),
        contentAlignment = Alignment.Center,
        // .background(Color.Black)
    ) {
        KamelImage(
            asyncPainterResource(imageLink),
            modifier = Modifier
                .fillMaxWidth()
                .blur(10.dp)
                .shadow(50.dp),
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
        )

        KamelImage(
            asyncPainterResource(imageLink),
            modifier = Modifier.align(Alignment.Center).clip(RoundedCornerShape(10.dp))
                .animateContentSize(spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                .fillMaxSize(if (showLargeImage) 0.7F else 0.35f).wrapContentHeight()
                .shadow(10.dp, ambientColor = Color.Green),
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
        )

        Box(
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().fillMaxHeight(.3f)
                .background(gradient),
        ) {
            Text(
                text,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp).align(
                    Alignment.BottomCenter,
                ),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }

    LaunchedEffect(true) {
        showLargeImage = false
        delay(500)
        showLargeImage = true
    }
}
