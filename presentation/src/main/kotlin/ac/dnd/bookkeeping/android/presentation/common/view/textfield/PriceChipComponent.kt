package ac.dnd.bookkeeping.android.presentation.common.view.textfield

import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Body1
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray150
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray300
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray700
import ac.dnd.bookkeeping.android.presentation.common.theme.Shapes
import ac.dnd.bookkeeping.android.presentation.common.theme.Space16
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PriceChipComponent(
    scope: CoroutineScope,
    chipPressColor: Color = Gray300,
    chipUnPressColor: Color = Gray150,
    chipContentColor: Color = Gray700,
    chipList: Map<String, Long> = mapOf(
        "1만" to 10_000,
        "5만" to 50_000,
        "10만" to 100_000,
        "50만" to 500_000
    ),
    onClickChip: (Long) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        chipList.forEach { (text, money) ->
            var isChipClick by remember { mutableStateOf(false) }
            val backgroundColorState = animateColorAsState(
                targetValue = if (isChipClick) chipPressColor else chipUnPressColor,
                label = "chip press state color"
            )
            Row(
                modifier = Modifier
                    .clip(Shapes.medium)
                    .background(color = backgroundColorState.value)
                    .clickable {
                        onClickChip(money)
                        scope.launch {
                            isChipClick = true
                            Thread.sleep(100L)
                            isChipClick = false
                        }
                    }
                    .padding(
                        horizontal = 8.dp,
                        vertical = 6.5.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_plus),
                    contentDescription = null,
                    modifier = Modifier.size(Space16)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = text,
                    style = Body1.merge(
                        color = chipContentColor,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}
