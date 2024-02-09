package ac.dnd.bookkeeping.android.presentation.common.view.textfield

import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Body1
import ac.dnd.bookkeeping.android.presentation.common.theme.Body2
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray400
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray600
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray800
import ac.dnd.bookkeeping.android.presentation.common.theme.Negative
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary3
import ac.dnd.bookkeeping.android.presentation.common.theme.Shapes
import ac.dnd.bookkeeping.android.presentation.common.theme.Space20
import ac.dnd.bookkeeping.android.presentation.common.theme.Space8
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TypingTextField(
    textType: TypingTextFieldType,
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hintText: String = "",
    isError: Boolean = false,
    isEnabled: Boolean = true,
    isSingleLine: Boolean = true,
    maxTextLength: Int = 100,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    backgroundColor: Color = Color.White,
    basicBorderColor: Color = Gray400,
    contentPadding: PaddingValues = PaddingValues(
        vertical = 13.5.dp,
        horizontal = 16.dp
    ),
    leadingIconContent: (@Composable () -> Unit)? = null,
    trailingIconContent: (@Composable () -> Unit)? = null,
    errorMessageContent: (@Composable () -> Unit) = { },
    onTextFieldFocusChange : (Boolean) -> Unit = {}
) {
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    var isTextFieldFocused by remember { mutableStateOf(false) }

    val currentColor =
        if (isError) Negative else if (isTextFieldFocused) Primary3 else basicBorderColor
    val currentColorState = animateColorAsState(
        targetValue = currentColor,
        label = "color state"
    )

    Column {
        Column(
            modifier = modifier
                .background(
                    color = backgroundColor,
                    shape = Shapes.medium
                )
                .border(
                    width = 1.dp,
                    shape = Shapes.medium,
                    color = currentColorState.value
                )
                .wrapContentHeight()
                .onFocusChanged {
                    isTextFieldFocused = it.isFocused
                    onTextFieldFocusChange(it.isFocused)
                }
        ) {
            BasicTextField(
                value = text,
                onValueChange = {
                    if (maxTextLength >= it.length) {
                        onValueChange(it)
                    }
                },
                enabled = isEnabled,
                modifier = Modifier.fillMaxWidth(),
                textStyle = Body1.merge(
                    color = Gray800
                ),
                singleLine = if (textType == TypingTextFieldType.LongSentence) false else isSingleLine,
                minLines = if (isSingleLine) 1 else 3,
                keyboardOptions = keyboardOptions,
                cursorBrush = SolidColor(value = currentColorState.value),
                interactionSource = interactionSource,
            ) { textField ->
                TextFieldDefaults.TextFieldDecorationBox(
                    value = text,
                    innerTextField = textField,
                    enabled = isEnabled,
                    singleLine = if (textType == TypingTextFieldType.LongSentence) false else isSingleLine,
                    visualTransformation = visualTransformation,
                    interactionSource = interactionSource,
                    placeholder = {
                        Text(
                            text = hintText,
                            style = Body1.merge(color = Gray600)
                        )
                    },
                    leadingIcon = leadingIconContent,
                    trailingIcon = trailingIconContent,
                    contentPadding = contentPadding
                )
            }

            if (textType == TypingTextFieldType.LongSentence) {
                Spacer(Modifier.height(Space20))
                Text(
                    text = "${text.length}/$maxTextLength",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 6.5.dp,
                            end = 16.dp,
                            bottom = 13.5.dp
                        ),
                    style = Body2.merge(color = Gray600),
                    textAlign = TextAlign.End
                )
            }
        }
        if (textType == TypingTextFieldType.Basic) {
            Spacer(Modifier.height(Space8))
            Box(
                modifier = Modifier.alpha(if (isError) 1f else 0f)
            ) {
                errorMessageContent()
            }
        }
    }
}

@Preview
@Composable
fun TypingTextField1Preview() {
    TypingTextField(
        TypingTextFieldType.Basic,
        text = "잘못 된 이름",
        hintText = "이름을 입력하세요.",
        modifier = Modifier.fillMaxWidth(),
        onValueChange = {},
        isError = true,
        errorMessageContent = {
            Text(
                text = "에러 발생",
                style = Body1.merge(color = Negative)
            )
        }
    )
}

@Preview
@Composable
fun TypingTextField2Preview() {
    TypingTextField(
        TypingTextFieldType.Basic,
        text = "이름",
        hintText = "이름을 입력하세요.",
        modifier = Modifier.fillMaxWidth(),
        onValueChange = {},
        isError = false,
        errorMessageContent = {
            Text(
                text = "에러 발생",
                style = Body1.merge(color = Negative)
            )
        }
    )
}

@Preview
@Composable
fun TypingTextField3Preview() {
    TypingTextField(
        TypingTextFieldType.Basic,
        text = "",
        hintText = "이름을 입력하세요.",
        modifier = Modifier.fillMaxWidth(),
        onValueChange = {},
        isError = false,
        trailingIconContent = {
            IconButton(
                onClick = { }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = "bottom icon",
                    modifier = Modifier.size(20.dp)
                )
            }
        },
    )
}

@Preview
@Composable
fun TypingTextField4Preview() {
    TypingTextField(
        TypingTextFieldType.LongSentence,
        text = "무릇은 경조사비 관리앱으로 사용자가 다양한 개인적인 축하 상황에 대해 금전적 기여를 쉽게 할 수 있게 돕는 모바일 애플리케이션입니다",
        hintText = "",
        onValueChange = {},

        )
}
