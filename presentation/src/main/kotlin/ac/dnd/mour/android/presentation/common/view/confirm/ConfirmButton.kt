package ac.dnd.mour.android.presentation.common.view.confirm

import ac.dnd.mour.android.presentation.common.theme.Body1
import ac.dnd.mour.android.presentation.common.theme.Caption2
import ac.dnd.mour.android.presentation.common.theme.Gray000
import ac.dnd.mour.android.presentation.common.theme.Gray300
import ac.dnd.mour.android.presentation.common.theme.Gray500
import ac.dnd.mour.android.presentation.common.theme.Gray700
import ac.dnd.mour.android.presentation.common.theme.Gray800
import ac.dnd.mour.android.presentation.common.theme.Headline2
import ac.dnd.mour.android.presentation.common.theme.Primary1
import ac.dnd.mour.android.presentation.common.theme.Primary4
import ac.dnd.mour.android.presentation.common.theme.Shapes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ConfirmButton(
    modifier: Modifier = Modifier,
    properties: ConfirmButtonProperties,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {},
    content: @Composable RowScope.(TextStyle) -> Unit
) {
    val textStyle = when (properties.size) {
        ConfirmButtonSize.Xlarge -> Headline2
        ConfirmButtonSize.Large -> Body1
        ConfirmButtonSize.Medium -> Body1
        ConfirmButtonSize.Small -> Caption2
    }

    val backgroundColor = when (properties.type) {
        ConfirmButtonType.Primary -> if (isEnabled) Primary4 else Gray500
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
        ConfirmButtonType.Outline -> BorderStroke(1.dp, Gray500)
    }

    val height = when (properties.size) {
        ConfirmButtonSize.Xlarge -> 52.dp
        ConfirmButtonSize.Large -> 48.dp
        ConfirmButtonSize.Medium -> 34.dp
        ConfirmButtonSize.Small -> 28.dp
    }

    val paddingHorizontal = when (properties.size) {
        ConfirmButtonSize.Xlarge -> 16.dp
        ConfirmButtonSize.Large -> 16.dp
        ConfirmButtonSize.Medium -> 8.dp
        ConfirmButtonSize.Small -> 8.dp
    }

    Button(
        modifier = modifier.height(height),
        shape = Shapes.large,
        contentPadding = PaddingValues(
            start = paddingHorizontal,
            top = 0.dp,
            bottom = 0.dp,
            end = paddingHorizontal
        ),
        colors = ButtonDefaults
            .textButtonColors(
                backgroundColor = backgroundColor
            ),
        border = border,
        elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
        onClick = onClick,
        enabled = isEnabled,
    ) {
        content(textStyle.merge(color = textColor))
    }
}

@Preview
@Composable
fun ConfirmButtonPreview1() {
    Column {
        ConfirmButton(
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Xlarge,
                type = ConfirmButtonType.Primary
            ),
            modifier = Modifier.fillMaxWidth()
        ) { style ->
            Text(
                text = "확인",
                style = style
            )
        }
        ConfirmButton(
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Xlarge,
                type = ConfirmButtonType.Secondary
            ),
            modifier = Modifier.fillMaxWidth()
        ) { style ->
            Text(
                text = "확인",
                style = style
            )
        }
        ConfirmButton(
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Xlarge,
                type = ConfirmButtonType.Tertiary
            ),
            modifier = Modifier.fillMaxWidth()
        ) { style ->
            Text(
                text = "확인",
                style = style
            )
        }
        ConfirmButton(
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Xlarge,
                type = ConfirmButtonType.Outline
            ),
            modifier = Modifier.fillMaxWidth()
        ) { style ->
            Text(
                text = "확인",
                style = style
            )
        }
    }
}

@Preview
@Composable
fun ConfirmButtonPreview2() {
    Column {
        ConfirmButton(
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Large,
                type = ConfirmButtonType.Primary
            ),
            modifier = Modifier.fillMaxWidth()
        ) { style ->
            Text(
                text = "확인",
                style = style
            )
        }
        ConfirmButton(
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Large,
                type = ConfirmButtonType.Secondary
            ),
            modifier = Modifier.fillMaxWidth()
        ) { style ->
            Text(
                text = "확인",
                style = style
            )
        }
        ConfirmButton(
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Large,
                type = ConfirmButtonType.Tertiary
            ),
            modifier = Modifier.fillMaxWidth()
        ) { style ->
            Text(
                text = "확인",
                style = style
            )
        }
        ConfirmButton(
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Large,
                type = ConfirmButtonType.Outline
            ),
            modifier = Modifier.fillMaxWidth()
        ) { style ->
            Text(
                text = "확인",
                style = style
            )
        }
    }
}

@Preview
@Composable
fun ConfirmButtonPreview3() {
    Column {
        ConfirmButton(
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Medium,
                type = ConfirmButtonType.Primary
            )
        ) { style ->
            Text(
                text = "확인",
                style = style
            )
        }
        ConfirmButton(
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Medium,
                type = ConfirmButtonType.Secondary
            )
        ) { style ->
            Text(
                text = "확인",
                style = style
            )
        }
        ConfirmButton(
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Medium,
                type = ConfirmButtonType.Tertiary
            )
        ) { style ->
            Text(
                text = "확인",
                style = style
            )
        }
        ConfirmButton(
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Medium,
                type = ConfirmButtonType.Outline
            )
        ) { style ->
            Text(
                text = "확인",
                style = style
            )
        }
    }
}

@Preview
@Composable
fun ConfirmButtonPreview4() {
    Column {
        ConfirmButton(
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Small,
                type = ConfirmButtonType.Primary
            )
        ) { style ->
            Text(
                text = "확인",
                style = style
            )
        }
        ConfirmButton(
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Small,
                type = ConfirmButtonType.Secondary
            )
        ) { style ->
            Text(
                text = "확인",
                style = style
            )
        }
        ConfirmButton(
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Small,
                type = ConfirmButtonType.Tertiary
            )
        ) { style ->
            Text(
                text = "확인",
                style = style
            )
        }
        ConfirmButton(
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Small,
                type = ConfirmButtonType.Outline
            )
        ) { style ->
            Text(
                text = "확인",
                style = style
            )
        }
    }
}
