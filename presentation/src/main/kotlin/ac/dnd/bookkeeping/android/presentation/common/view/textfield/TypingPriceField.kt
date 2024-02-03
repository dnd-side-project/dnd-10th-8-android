package ac.dnd.bookkeeping.android.presentation.common.view.textfield

import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Body1
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray500
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray700
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray800
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline3
import ac.dnd.bookkeeping.android.presentation.common.theme.Negative
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary3
import ac.dnd.bookkeeping.android.presentation.common.util.expansion.NumberCommaTransformation
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
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
    hintText: String = "지출하신 금액을 입력해주세요",
    isError: Boolean = false,
    isEnabled: Boolean = true,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    textFieldHeight: Dp = 35.dp,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
    textFormat : VisualTransformation = NumberCommaTransformation(),
    fieldSubjectContent: (@Composable () -> Unit) = { FieldSubject() },
    leadingIconContent: (@Composable () -> Unit)? = null,
    trailingIconContent: (@Composable () -> Unit)? = null,
    errorMessageContent: (@Composable () -> Unit) = { },
) {
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    var isTextFieldFocused by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val currentColor = if (isError) Negative else if (isTextFieldFocused) Primary3 else Gray500
    val currentColorState = animateColorAsState(
        targetValue = currentColor,
        label = "color state"
    )

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
                color = Gray800,
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
                        style = Headline3.merge(
                            color = Gray500,
                            fontWeight = FontWeight.SemiBold
                        )
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

@Composable
private fun FieldSubject() {
    Column {
        Row {
            Text(
                text = "금액",
                style = Body1.merge(
                    color = Gray700,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(modifier = Modifier.width(1.dp))
            Box(
                modifier = Modifier
                    .height(21.dp)
                    .padding(bottom = 3.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_essential_field),
                    contentDescription = null
                )
            }
        }
        Spacer(
            modifier = Modifier.height(18.dp)
        )
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