
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import screens.home.HomeScreen

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    // val presenter = remember { DrinksSearchPresenter() }
    MaterialTheme {
        Navigator(HomeScreen)

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
