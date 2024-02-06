package ac.dnd.bookkeeping.android.presentation.common.view.component

import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Body1
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray000
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray400
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray800
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary4
import ac.dnd.bookkeeping.android.presentation.common.theme.Shapes
import ac.dnd.bookkeeping.android.presentation.common.theme.Space12
import ac.dnd.bookkeeping.android.presentation.common.theme.Space16
import ac.dnd.bookkeeping.android.presentation.common.theme.Space40
import ac.dnd.bookkeeping.android.presentation.common.theme.Space48
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FieldSelectComponent(
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(Shapes.medium)
            .background(color = Gray000)
            .border(
                width = 1.dp,
                color = if (isSelected) Primary4 else Gray400,
                shape = Shapes.medium
            )
            .fillMaxWidth()
            .height(Space48)
            .clickable {
                onClick()
            }
            .padding(
                start = Space16,
                end = Space12,
                top = Space12,
                bottom = Space12
            )
    ) {
        Text(
            text = text,
            style = Body1.merge(
                color = Gray800,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(end = Space40),
            overflow = TextOverflow.Ellipsis
        )

        Image(
            painter = painterResource(R.drawable.ic_chevron_right),
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun FieldSelectComponentPreview() {
    FieldSelectComponent(
        isSelected = true,
        text = "2024/01/10",
        onClick = {}
    )
}
