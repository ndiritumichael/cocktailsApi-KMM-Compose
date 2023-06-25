package screens.DrinkDetailsScreen

import AsyncImage
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
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
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

import presentation.DrinksSearchPresenter

data class DrinksDetailScreen(val drinkId: String) : Screen, KoinComponent {
    private val presenter: DrinksSearchPresenter by inject()

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val drinkState = presenter.drinkDetailState.collectAsState().value
        val navigator = LocalNavigator.currentOrThrow
        val gradient = Brush.verticalGradient(listOf(Color.Transparent, Color.Transparent, Color.Black))
        var selectedLanguageIndex by remember { mutableStateOf(0) }
        LaunchedEffect(true) {
            presenter.getDrinkDetails(drinkId)
            // presenter.
        }
        Scaffold {
            Box(
                modifier = Modifier.fillMaxSize().padding(top = 100.dp),
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
                        Box(
                            modifier = Modifier.fillMaxWidth()
                                .height(400.dp),
                            // .background(Color.Black)
                        ) {
                            AsyncImage(
                                drink.drinkImage,
                                modifier = Modifier.fillMaxWidth(),
                                contentScale = ContentScale.FillWidth,
                            )
                            Box(
                                modifier = Modifier.fillMaxSize()
                                    .background(gradient),
                            )
                            Text(drink.name, color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.BottomCenter).padding(8.dp), style = MaterialTheme.typography.h4)
                        }

                    }

                    item {
                        HorizontalPager(pageCount = drink.ingredient.size, pageSize = PageSize.Fixed(105.dp)){
                            val ingredients = drink.ingredient[it]
                            Card (modifier = Modifier.width(100.dp)){
                                Column {
                                    Text(ingredients.name, fontWeight = FontWeight.Bold)
                                    Text(ingredients.measurements)
                                }
                            }
                        }
                    }
                    item {
                        Column {
                            Text("Instructions", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.h4)
                            TabRow(selectedTabIndex = selectedLanguageIndex){
                                drink.instructions.mapIndexed { index,instruction ->
                                Tab(selected = selectedLanguageIndex == index, onClick = {
                                    selectedLanguageIndex = index

                                },
                                text = { Text(instruction.language) })


                                }

                            }

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
