package ac.dnd.mour.android.presentation.common.view.textfield

import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.theme.Body1
import ac.dnd.mour.android.presentation.common.theme.Gray400
import ac.dnd.mour.android.presentation.common.theme.Gray500
import ac.dnd.mour.android.presentation.common.theme.Gray700
import ac.dnd.mour.android.presentation.common.theme.Gray900
import ac.dnd.mour.android.presentation.common.theme.Primary4
import ac.dnd.mour.android.presentation.common.theme.Shapes
import ac.dnd.mour.android.presentation.common.theme.Space20
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserDateTypingField(
    hintText: String,
    text: String,
    onValueChange: (String) -> Unit,
    onTextFieldFocusChange: (Boolean) -> Unit = {},
    onFiledReset: () -> Unit
) {
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    var isTextFieldFocused by remember { mutableStateOf(false) }
    val currentColor = if (isTextFieldFocused) Primary4 else Gray400
    val currentColorState = animateColorAsState(
        targetValue = currentColor,
        label = "color state"
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                shape = Shapes.medium,
                color = currentColorState.value
            )
            .background(
                color = Color.White,
                shape = Shapes.medium
            )
            .onFocusChanged {
                isTextFieldFocused = it.isFocused
                onTextFieldFocusChange(it.isFocused)
            }
    ) {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .wrapContentWidth()
                .height(48.dp),
            textStyle = Body1.merge(
                color = Gray900,
                fontWeight = FontWeight.Normal
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions.Default,
            cursorBrush = SolidColor(value = currentColorState.value),
            interactionSource = interactionSource,
        ) { textField ->
            TextFieldDefaults.TextFieldDecorationBox(
                value = text,
                innerTextField = textField,
                enabled = true,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                placeholder = {
                    Text(
                        text = hintText,
                        style = Body1.merge(
                            color = Gray700,
                            fontWeight = FontWeight.Normal
                        )
                    )
                },
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 13.5.dp
                ),
            )
        }
        if (isTextFieldFocused) {
            Box(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(20.dp)
                    .align(Alignment.CenterEnd)
            ) {
                if (text.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            onFiledReset()
                        }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_close_circle),
                            contentDescription = "close icon",
                            modifier = Modifier.size(Space20)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun UserDateTypingFieldPreview() {
    Row {
        UserDateTypingField(
            text = " 0000",
            onTextFieldFocusChange = {

            },
            onValueChange = {

            },
            hintText = "xxxx",
            onFiledReset = {}
        )
    }
}
