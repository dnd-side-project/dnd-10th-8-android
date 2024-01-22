package ac.dnd.bookkeeping.android.presentation.common.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SnackBarScreen(message: String) {
    Box(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .background(
                shape = RoundedCornerShape(10.dp),
                color = Color.LightGray
            ),
    ) {
        Text(
            text = message,
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier.padding(20.dp),
        )
    }
}
