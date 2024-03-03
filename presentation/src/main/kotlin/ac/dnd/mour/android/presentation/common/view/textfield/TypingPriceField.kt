package ac.dnd.mour.android.presentation.common.view.textfield

import ac.dnd.mour.android.presentation.common.theme.Gray500
import ac.dnd.mour.android.presentation.common.theme.Gray600
import ac.dnd.mour.android.presentation.common.theme.Gray900
import ac.dnd.mour.android.presentation.common.theme.Headline3
import ac.dnd.mour.android.presentation.common.theme.Negative
import ac.dnd.mour.android.presentation.common.theme.Primary3
import ac.dnd.mour.android.presentation.common.util.expansion.NumberCommaTransformation
import ac.dnd.mour.android.presentation.common.view.component.FieldSubject
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TypingPriceField(
    modifier: Modifier = Modifier,
    textValue: String,
    onValueChange: (String) -> Unit,
    clearFocus: Boolean = true,
    hintText: String = "지출하신 금액을 입력해주세요",
    isError: Boolean = false,
    isEnabled: Boolean = true,
    isAddFiledEnabled: Boolean = true,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    textFieldHeight: Dp = 35.dp,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
    textFormat: VisualTransformation = NumberCommaTransformation(),
    fieldSubjectContent: (@Composable () -> Unit) = { FieldSubject("금액") },
    leadingIconContent: (@Composable () -> Unit)? = null,
    trailingIconContent: (@Composable () -> Unit)? = null,
    errorMessageContent: (@Composable () -> Unit) = { },
) {
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    var isTextFieldFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val currentColor = if (isError) Negative else if (isTextFieldFocused) Primary3 else Gray500
    val currentColorState = animateColorAsState(
        targetValue = currentColor,
        label = "color state"
    )

    if (clearFocus) {
        LaunchedEffect(Unit) {
            focusManager.clearFocus()
        }
    }

    fun makeFilterText(text: String): String = text.filter { it.isDigit() }

    fun addPriceFormat(addPrice: Long): String {
        val prevPrice = if (textValue.isEmpty()) 0L else makeFilterText(textValue).toLong()
        return (addPrice + prevPrice).toString()
    }

    Column(
        modifier = modifier
            .background(color = Color.Transparent)
            .onFocusChanged {
                isTextFieldFocused = it.isFocused
            }
    ) {
        fieldSubjectContent()
        Spacer(modifier = Modifier.height(18.dp))
        BasicTextField(
            value = TextFieldValue(
                text = textValue,
                selection = TextRange(textValue.length)
            ),
            onValueChange = {
                val newText = makeFilterText(it.text)
                onValueChange(newText)
            },
            enabled = isEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(textFieldHeight),
            textStyle = Headline3.merge(
                color = Gray900,
                fontWeight = FontWeight.SemiBold
            ),
            singleLine = true,
            keyboardOptions = keyboardOptions,
            cursorBrush = SolidColor(value = currentColorState.value),
            visualTransformation = textFormat,
            interactionSource = interactionSource
        ) { textField ->
            TextFieldDefaults.TextFieldDecorationBox(
                value = TextFieldValue(
                    text = textValue,
                    selection = TextRange(textValue.length)
                ).text,
                innerTextField = textField,
                enabled = isEnabled,
                singleLine = true,
                placeholder = {
                    Text(
                        text = hintText,
                        fontWeight = FontWeight.SemiBold,
                        style = Headline3.merge(color = Gray600)
                    )
                },
                visualTransformation = textFormat,
                interactionSource = interactionSource,
                leadingIcon = leadingIconContent,
                trailingIcon = trailingIconContent,
                contentPadding = innerPadding
            )
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp),
            color = currentColorState.value
        )
        if (isAddFiledEnabled || isTextFieldFocused) {
            Spacer(modifier = Modifier.height(6.dp))
            PriceChipComponent(
                scope = scope,
                onClickChip = { addPrice ->
                    val newText = addPriceFormat(addPrice)
                    onValueChange(newText)
                }
            )
            errorMessageContent()
        }
    }
}

@Composable
@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
fun EnterMoneyField1Preivew() {
    TypingPriceField(
        textValue = "1111",
        modifier = Modifier.fillMaxWidth(),
        onValueChange = {},
    )
}

@Composable
@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
fun EnterMoneyField2Preivew() {
    TypingPriceField(
        textValue = "",
        modifier = Modifier.fillMaxWidth(),
        onValueChange = {},
    )
}

@Composable
@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
fun EnterMoneyField3Preview() {
    var input by remember { mutableStateOf("10000000") }
    TypingPriceField(
        textValue = input,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        onValueChange = {
            input = it
        },
    )
}
