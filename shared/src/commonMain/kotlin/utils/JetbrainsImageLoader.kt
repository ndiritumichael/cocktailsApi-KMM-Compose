//package utils
//
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.graphics.ImageBitmap
//import androidx.compose.ui.graphics.painter.Painter
//import androidx.compose.ui.unit.Density
//
//
//@Composable
//fun <T> utils.AsyncImage(
//    load: suspend () -> T,
//    painterFor: @Composable (T) -> Painter,
//    contentDescription: String,
//    modifier: Modifier = Modifier,
//    contentScale: ContentScale = ContentScale.Fit,
//) {
//    val image: T? by produceState<T?>(null) {
//        value = withContext(Dispatchers.IO) {
//            try {
//                load()
//            } catch (e: IOException) {
//                // instead of printing to console, you can also write this to log,
//                // or show some error placeholder
//                e.printStackTrace()
//                null
//            }
//        }
//    }
//
//    if (image != null) {
//        Image(
//            painter = painterFor(image!!),
//            contentDescription = contentDescription,
//            contentScale = contentScale,
//            modifier = modifier
//        )
//    }
//}
//
///* Loading from file with java.io API */
//
//fun loadImageBitmap(file: File): ImageBitmap =
//    file.inputStream().buffered().use(::loadImageBitmap)
//
//fun loadSvgPainter(file: File, density: Density): Painter =
//    file.inputStream().buffered().use { loadSvgPainter(it, density) }
//
//fun loadXmlImageVector(file: File, density: Density): ImageVector =
//    file.inputStream().buffered().use { loadXmlImageVector(InputSource(it), density) }
//
///* Loading from network with java.net API */
//
//fun loadImageBitmap(url: String): ImageBitmap =
//    URL(url).openStream().buffered().use(::loadImageBitmap)
//
//fun loadSvgPainter(url: String, density: Density): Painter =
//    URL(url).openStream().buffered().use { loadSvgPainter(it, density) }
//
//fun loadXmlImageVector(url: String, density: Density): ImageVector =
//    URL(url).openStream().buffered().use { loadXmlImageVector(InputSource(it), density) }