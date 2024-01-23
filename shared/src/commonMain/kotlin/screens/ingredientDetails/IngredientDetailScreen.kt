package screens.ingredientDetails

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kmpalette.loader.rememberNetworkLoader
import com.kmpalette.rememberDominantColorState
import com.kmpalette.rememberPaletteState
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.http.Url
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import screens.drinkDetailsScreen.DrinksDetailScreen
import screens.searchScreen.CocktailListGrid
import screens.uiutils.getLargeIngredientImage

data class IngredientDetailScreen(val id: Int, val name: String) : Screen, KoinComponent {
    val presenter: IngredientDrinkPresenter by inject()

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val drinkdetailsState by presenter.ingredientDrinkState.collectAsState()
        var backButtonColor by remember {
            mutableStateOf(Color.Black)
        }

        var expandedDetails by remember {
            mutableStateOf(false)
        }
        val maxLines: Int by animateIntAsState(if (expandedDetails) 100 else 3)






        LaunchedEffect(id) {
            presenter.getIngredientDetails(id, name)
        }

        Scaffold(topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    name,
                    fontWeight = FontWeight.Bold,

                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier.basicMarquee()
                )
            }, navigationIcon = {
                IconButton(
                    { navigator.pop() },
                    modifier = Modifier.clip(CircleShape)
                        .background(backButtonColor.copy(0.5f)),
                ) {
                    Icon(Icons.Filled.ArrowBack, null, tint = Color.Black)
                }
            })
        }) { paddingValues ->
            when (val state = drinkdetailsState) {
                is IngredientDrinkState.Failure -> {}
                IngredientDrinkState.Idle -> {}
                IngredientDrinkState.Loading -> {}
                is IngredientDrinkState.Success -> {
                    LazyColumn(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                        item {
                            IngredientPoster(
                                getLargeIngredientImage(state.drinks.name),
                                state.drinks.name,
                            ) {
                                backButtonColor = it
                            }
                        }

                        item {
                            Text(
                                state.drinks.description,
                                modifier = Modifier.padding(8.dp)
                                    .clickable {
                                        expandedDetails = expandedDetails.not()
                                    },
                                maxLines = maxLines,
                                overflow = TextOverflow.Ellipsis,
                                softWrap = true,
                            )
                        }
                        item {
                            Text(
                                "Drinks made with $name",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                            )
                        }
                        item {
                            CocktailListGrid(
                                modifier = Modifier.height(700.dp),
                                state.drinks.drinksList,
                            ) { _, drink ->
                                navigator.push(DrinksDetailScreen(drink))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun IngredientPoster(imageLink: String, text: String, onDominantColorChange: (Color) -> Unit) {
    val networkLoader = rememberNetworkLoader()
    val dominantPal = rememberDominantColorState(loader = networkLoader)
    val colorSwatch = rememberPaletteState(networkLoader)
    val gradient = Brush.verticalGradient(
        listOf(
            Color.Transparent,

            dominantPal.color.copy(alpha = 0.5f),
            dominantPal.color,
        ),
    )
    var showLargeImage by remember {
        mutableStateOf(false)
    }
    val changeColor = remember { onDominantColorChange }

    Box(
        modifier = Modifier.fillMaxWidth()
            .height(350.dp),
        contentAlignment = Alignment.Center,
        // .background(Color.Black)
    ) {
        Box(modifier = Modifier.fillMaxSize().background(gradient))

        KamelImage(
            asyncPainterResource(imageLink),
            modifier = Modifier.align(Alignment.Center).clip(RoundedCornerShape(10.dp))
                .animateContentSize(tween(durationMillis = 1500, easing = FastOutLinearInEasing))
                .fillMaxSize(if (showLargeImage) 0.8F else 0.35f),
            contentScale = ContentScale.Fit,
            contentDescription = null,
        )
    }

    LaunchedEffect(imageLink) {
        dominantPal.updateFrom(Url(imageLink))
        // colorSwatch.generate(Url(imageLink))
    }
    LaunchedEffect(true) {

        showLargeImage = true
    }

    LaunchedEffect(colorSwatch.palette) {
        println(colorSwatch.palette?.swatches)
    }

    LaunchedEffect(dominantPal.color) {
        changeColor(dominantPal.color)
    }
}
