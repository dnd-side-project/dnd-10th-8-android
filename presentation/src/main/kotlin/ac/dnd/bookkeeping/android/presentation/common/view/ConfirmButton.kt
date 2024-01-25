package ac.dnd.bookkeeping.android.presentation.common.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ConfirmButton(
    text: String,
    isMain: Boolean,
    modifier: Modifier,
    isLoading: Boolean = false,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 10.dp),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = if (isMain) Color.Cyan else Color.LightGray
        ),
        elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
        onClick = onClick,
        enabled = isEnabled && !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Text(
                text = text,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview
@Composable
fun ConfirmButtonPreview1() {
    ConfirmButton(
        text = "확인",
        isMain = true,
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
    )
}

@Preview
@Composable
fun ConfirmButtonPreview2() {
    ConfirmButton(
        text = "취소",
        isMain = false,
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
    )
}

@Preview
@Composable
fun ConfirmButtonPreview3() {
    ConfirmButton(
        text = "다음",
        isMain = true,
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
        isEnabled = false
    )
}

@Preview
@Composable
fun ConfirmButtonPreview4() {
    ConfirmButton(
        text = "로딩",
        isMain = true,
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
        isLoading = true
    )
}
