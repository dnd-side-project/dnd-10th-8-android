package ac.dnd.mour.android.presentation.common.view

import ac.dnd.mour.android.presentation.common.theme.Body1
import ac.dnd.mour.android.presentation.common.theme.Gray100
import ac.dnd.mour.android.presentation.common.theme.Gray800
import ac.dnd.mour.android.presentation.common.theme.Shapes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SnackBarScreen(message: String) {
    Box(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .background(
                shape = Shapes.medium,
                color = Gray800
            ),
    ) {
        Text(
            text = message,
            style = Body1.merge(
                color = Gray100,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 8.dp
                )
                .padding(vertical = 14.dp)
        )
    }
}
