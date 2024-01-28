package ac.dnd.bookkeeping.android.presentation.common.view.confirm

import ac.dnd.bookkeeping.android.presentation.common.theme.Body1
import ac.dnd.bookkeeping.android.presentation.common.theme.Caption2
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray000
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray300
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray700
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray800
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline2
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary1
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary4
import ac.dnd.bookkeeping.android.presentation.common.theme.Shapes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ConfirmButton(
    text: String,
    modifier: Modifier = Modifier,
    properties: ConfirmButtonProperties,
    isLoading: Boolean = false,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    val textStyle = when (properties.size) {
        ConfirmButtonSize.Xlarge -> Headline2
        ConfirmButtonSize.Large -> Body1
        ConfirmButtonSize.Medium -> Body1
        ConfirmButtonSize.Small -> Caption2
    }

    val backgroundColor = when (properties.type) {
        ConfirmButtonType.Primary -> Primary4
        ConfirmButtonType.Secondary -> Gray300
        ConfirmButtonType.Tertiary -> Primary1
        ConfirmButtonType.Outline -> Gray000
    }

    val textColor = when (properties.type) {
        ConfirmButtonType.Primary -> Gray000
        ConfirmButtonType.Secondary -> Gray700
        ConfirmButtonType.Tertiary -> Primary4
        ConfirmButtonType.Outline -> Gray800
    }

    val border = when (properties.type) {
        ConfirmButtonType.Primary -> null
        ConfirmButtonType.Secondary -> null
        ConfirmButtonType.Tertiary -> null
        ConfirmButtonType.Outline -> BorderStroke(1.dp, Gray800)
    }

    RoundedCornerShape(10.dp)
    Button(
        modifier = modifier,
        shape = Shapes.large,
        contentPadding = PaddingValues(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 10.dp),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = backgroundColor
        ),
        border = border,
        elevation = ButtonDefaults.elevation(0.dp),
        onClick = onClick,
        enabled = isEnabled && !isLoading,
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Text(
                text = text,
                style = textStyle.merge(color = textColor)
            )
        }
    }
}

@Preview
@Composable
fun ConfirmButtonPreview1() {
    Column {
        ConfirmButton(
            text = "확인",
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Xlarge,
                type = ConfirmButtonType.Primary
            ),
            modifier = Modifier.fillMaxWidth()
        )
        ConfirmButton(
            text = "확인",
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Xlarge,
                type = ConfirmButtonType.Secondary
            ),
            modifier = Modifier.fillMaxWidth()
        )
        ConfirmButton(
            text = "확인",
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Xlarge,
                type = ConfirmButtonType.Tertiary
            ),
            modifier = Modifier.fillMaxWidth()
        )
        ConfirmButton(
            text = "확인",
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Xlarge,
                type = ConfirmButtonType.Outline
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun ConfirmButtonPreview2() {
    Column {
        ConfirmButton(
            text = "확인",
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Large,
                type = ConfirmButtonType.Primary
            ),
            modifier = Modifier.fillMaxWidth()
        )
        ConfirmButton(
            text = "확인",
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Large,
                type = ConfirmButtonType.Secondary
            ),
            modifier = Modifier.fillMaxWidth()
        )
        ConfirmButton(
            text = "확인",
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Large,
                type = ConfirmButtonType.Tertiary
            ),
            modifier = Modifier.fillMaxWidth()
        )
        ConfirmButton(
            text = "확인",
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Large,
                type = ConfirmButtonType.Outline
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun ConfirmButtonPreview3() {
    Column {
        ConfirmButton(
            text = "확인",
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Medium,
                type = ConfirmButtonType.Primary
            )
        )
        ConfirmButton(
            text = "확인",
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Medium,
                type = ConfirmButtonType.Secondary
            )
        )
        ConfirmButton(
            text = "확인",
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Medium,
                type = ConfirmButtonType.Tertiary
            )
        )
        ConfirmButton(
            text = "확인",
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Medium,
                type = ConfirmButtonType.Outline
            )
        )
    }
}

@Preview
@Composable
fun ConfirmButtonPreview4() {
    Column {
        ConfirmButton(
            text = "확인",
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Small,
                type = ConfirmButtonType.Primary
            )
        )
        ConfirmButton(
            text = "확인",
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Small,
                type = ConfirmButtonType.Secondary
            )
        )
        ConfirmButton(
            text = "확인",
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Small,
                type = ConfirmButtonType.Tertiary
            )
        )
        ConfirmButton(
            text = "확인",
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Small,
                type = ConfirmButtonType.Outline
            )
        )
    }
}
