package utils

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.readBytes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.skia.Image

object ImageUtils {

    private val client = HttpClient()

    private val inMemoryCache = mutableMapOf<String, ByteArray>()

    suspend fun getImageBitmapByUrl(url: String): ImageBitmap {
        val bytes = inMemoryCache.getOrPut(url) {
            client.get(url).readBytes()
        }
        val bitmap = withContext(Dispatchers.Default) {
            Image.makeFromEncoded(bytes).toComposeImageBitmap()
        }
        return bitmap
    }
}
val data  = listOf<Int>(1,2,3,4,5,6,7,8,9)