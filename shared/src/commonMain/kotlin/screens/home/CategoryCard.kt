package screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CategoryCard(name: String, color: Color, onCategoryClicked: () -> Unit) {
    val gradient = Brush.linearGradient(listOf(color.copy(0.3f), color.copy(0.1f)))
    Card(
        onClick = onCategoryClicked,
        modifier = Modifier.fillMaxWidth().height(175.dp).padding(bottom = 16.dp, end = 16.dp, start = 8.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Box(modifier = Modifier.fillMaxSize().background(brush = gradient)) {
            Text(name, modifier = Modifier.align(Alignment.Center), fontWeight = FontWeight.ExtraBold)

            Image(
                painterResource("cocktailsvg.xml"),
                null,
                colorFilter = ColorFilter.tint(color),
                modifier = Modifier.size(50.dp).align(Alignment.BottomEnd).padding(bottom = 10.dp, end = 10.dp),
            )
        }
    }
}
