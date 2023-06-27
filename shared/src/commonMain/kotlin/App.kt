
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import io.github.aakira.napier.Napier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import screens.searchScreen.SearchScreen

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    // val presenter = remember { DrinksSearchPresenter() }
    MaterialTheme {
        Napier.e { "Starting app " }
        var greetingText by remember { mutableStateOf("Hello, World!") }
        var showImage by remember { mutableStateOf(true) }
        val showLargeImage by animateDpAsState(
            targetValue = if (showImage) 400.dp else 100.dp,
            spring(dampingRatio = Spring.DampingRatioHighBouncy),
        )
        Navigator(SearchScreen)

//        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//            Button(onClick = {
//                greetingText = "Hello, ${getPlatformName()}"
//                showImage = !showImage
//            }) {
//                Text(greetingText)
//            }
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                AnimatedVisibility(true) {
//                    Image(
//                        painterResource("compose-multiplatform.xml"),
//                        null,
//                    )
//                }
//
//                AsyncImage(
//                    imageUrl = "https://media.kitsu.io/anime/poster_images/12/large.jpg",
//                    contentScale = ContentScale.Fit,
//                    modifier = Modifier.size(showLargeImage).clip(shape = RoundedCornerShape(50.dp)),
//                    loadingPlaceHolder = {
//                        CircularProgressIndicator(modifier = Modifier.size(50.dp).align(Alignment.Center))
//                    },
//                )
//            }
//        }
    }
}

expect fun getPlatformName(): String
