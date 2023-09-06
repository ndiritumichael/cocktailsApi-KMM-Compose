package screens.common.tabs

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
private fun MyTabIndicator(
    indicatorWidth: Dp,
    indicatorOffset: Dp,
    indicatorColor: Color,
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(
                width = indicatorWidth,
            )
            .offset(
                x = indicatorOffset,
            )
            .clip(
                shape = RoundedCornerShape(16.dp),
            )
            .background(
                color = indicatorColor,
            ),
    )
}

@Composable
private fun MyTabItem(
    isSelected: Boolean,
    onClick: () -> Unit,
    tabWidth: Dp,
    text: String,
) {
    val tabTextColor: Color by animateColorAsState(
        targetValue = if (isSelected) {
            White
        } else {
            Black
        },
        animationSpec = tween(easing = LinearEasing),
    )

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(
                width = tabWidth,
            ),
    ) {
        Text(
            modifier = Modifier
                .clip(CircleShape)
                .clickable {
                    onClick()
                }
                .width(tabWidth)
                .fillMaxHeight()
                .padding(
                    vertical = 8.dp,
                    horizontal = 12.dp,
                ),
            text = text,
            color = tabTextColor,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun CustomTab(
    selectedItemIndex: Int,
    items: List<String>,
    modifier: Modifier = Modifier,
    tabWidth: Dp = 100.dp,
    alignment: Alignment = Alignment.Center,
    onClick: (index: Int) -> Unit,
) {
    val indicatorOffset: Dp by animateDpAsState(
        targetValue = tabWidth * selectedItemIndex,
        animationSpec = tween(easing = LinearEasing),
    )
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = alignment) {
        Box(
            modifier = modifier
                .clip(CircleShape)
                .background(White)
                .height(intrinsicSize = IntrinsicSize.Min)
                .padding(4.dp),

        ) {
            MyTabIndicator(
                indicatorWidth = tabWidth,
                indicatorOffset = indicatorOffset,
                indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(8.dp).clip(CircleShape).fillMaxHeight(),
            ) {
                items.mapIndexed { index, text ->
                    val isSelected = index == selectedItemIndex
                    MyTabItem(
                        isSelected = isSelected,
                        onClick = {
                            onClick(index)
                        },
                        tabWidth = tabWidth,
                        text = text,
                    )
                }
            }
        }
    }
}
