
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale

expect fun AsyncImage(
    imageUrl: String,
    loadingPlaceHolder: @Composable BoxScope.() -> Unit = {},
    errorPlaceHolder: @Composable BoxScope.() -> Unit = {},
    contentDescription: String? =null,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.None,
    alpha: Float = 1f,
    coloFilter: ColorFilter? = null,
    filterQuality: FilterQuality = FilterQuality.None,
)
