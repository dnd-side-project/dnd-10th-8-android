package ac.dnd.bookkeeping.android.presentation.common.view

import ac.dnd.bookkeeping.android.presentation.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomTextField(
    text: String,
    modifier: Modifier = Modifier,
    isEnabled: (Boolean) = true,
    isSingleLine: (Boolean) = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    height: Dp = 40.dp,
    elevation: Dp = 0.dp,
    contentOuterPadding: PaddingValues = PaddingValues(all = 0.dp),
    contentInnerPadding: PaddingValues = PaddingValues(all = 0.dp),
    textStyle: TextStyle = TextStyle(fontSize = 12.sp, color = Color.Black),
    keyboardType: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    cursorBrush: SolidColor = SolidColor(value = Color.Black),
    cornerShape: Shape = RoundedCornerShape(size = 8.dp),
    cornerBorder: BorderStroke = BorderStroke(width = 1.dp, color = Color.Black),
    backgroundColor: Color = MaterialTheme.colors.background,
    onTextChange: (String) -> Unit = {},
    hintTextContent: @Composable () -> Unit = {},
    leadingIconContent: (@Composable () -> Unit)? = null,
    trailingIconContent: (@Composable () -> Unit)? = null
) {
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    Surface(
        shape = cornerShape,
        border = cornerBorder,
        color = backgroundColor,
        modifier = Modifier
            .padding(contentOuterPadding)
            .height(height)
            .shadow(elevation = elevation, shape = cornerShape)
    ) {
        BasicTextField(
            value = text,
            onValueChange = { changedText ->
                onTextChange(changedText)
            },
            enabled = isEnabled,
            textStyle = textStyle,
            modifier = modifier,
            keyboardOptions = keyboardType,
            cursorBrush = cursorBrush,
            interactionSource = interactionSource,
        ) { textField ->
            TextFieldDefaults.TextFieldDecorationBox(
                value = text,
                innerTextField = textField,
                enabled = isEnabled,
                singleLine = isSingleLine,
                visualTransformation = visualTransformation,
                interactionSource = interactionSource,
                placeholder = { hintTextContent() },
                leadingIcon = leadingIconContent,
                trailingIcon = trailingIconContent,
                contentPadding = contentInnerPadding
            )
        }
    }
}

@Preview()
@Composable
fun CustomTextFieldPreviewNormal() {
    CustomTextField(
        text = "custom Text",
        modifier = Modifier.fillMaxSize(),
        contentInnerPadding = PaddingValues(horizontal = 10.dp)
    )
}

@Preview()
@Composable
fun CustomTextFieldPreviewWithIcon() {
    CustomTextField(
        text = "custom Text",
        modifier = Modifier.fillMaxSize(),
        height = 50.dp,
        leadingIconContent = {
            IconButton(
                onClick = {}
            ) {
                Box(
                    modifier = Modifier.size(30.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "",
                        tint = Color.Black
                    )
                }
            }
        },
        trailingIconContent = {
            IconButton(
                onClick = {}
            ) {
                Box(
                    modifier = Modifier.size(30.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "",
                        tint = Color.Black
                    )
                }
            }
        }
    )
}
