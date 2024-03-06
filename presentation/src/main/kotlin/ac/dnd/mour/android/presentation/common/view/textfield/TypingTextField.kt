package ac.dnd.mour.android.presentation.common.view.textfield

import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.theme.Body1
import ac.dnd.mour.android.presentation.common.theme.Body2
import ac.dnd.mour.android.presentation.common.theme.Gray400
import ac.dnd.mour.android.presentation.common.theme.Gray500
import ac.dnd.mour.android.presentation.common.theme.Gray600
import ac.dnd.mour.android.presentation.common.theme.Gray700
import ac.dnd.mour.android.presentation.common.theme.Gray900
import ac.dnd.mour.android.presentation.common.theme.Negative
import ac.dnd.mour.android.presentation.common.theme.Primary4
import ac.dnd.mour.android.presentation.common.theme.Shapes
import ac.dnd.mour.android.presentation.common.theme.Space8
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TypingTextField(
    textType: TypingTextFieldType,
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hintText: String = "",
    clearFocus: Boolean = true ,
    isError: Boolean = false,
    isEnabled: Boolean = true,
    isSingleLine: Boolean = true,
    maxTextLength: Int = 100,
    fieldHeight: Dp = 48.dp,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    backgroundColor: Color = Color.White,
    basicBorderColor: Color = Gray500,
    cursorColor: Color? = null,
    hintTextColor: Color = Gray700,
    textStyle: TextStyle = Body1.merge(
        color = Gray900,
        fontWeight = FontWeight.Normal
    ),
    contentPadding: PaddingValues = PaddingValues(
        horizontal = 16.dp,
        vertical = 13.5.dp
    ),
    leadingIconContent: (@Composable () -> Unit) = {},
    trailingIconContent: (@Composable () -> Unit)? = null,
    errorMessageContent: (@Composable () -> Unit) = { },
    onTextFieldFocusChange: (Boolean) -> Unit = {}
) {

    val focusManager = LocalFocusManager.current
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    var isTextFieldFocused by remember { mutableStateOf(false) }

    val currentColor =
        if (isError) Negative else if (isTextFieldFocused) Primary4 else basicBorderColor
    val currentColorState = animateColorAsState(
        targetValue = currentColor,
        label = "color state"
    )

    if (clearFocus) {
        LaunchedEffect(Unit) {
            focusManager.clearFocus()
        }
    }

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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                leadingIconContent()
                BasicTextField(
                    value = text,
                    onValueChange = {
                        if (maxTextLength >= it.length) {
                            onValueChange(it)
                        }
                    },
                    enabled = isEnabled,
                    modifier = if (fieldHeight == 0.dp) {
                        Modifier
                            .weight(1f)
                            .wrapContentHeight()
                    } else {
                        Modifier
                            .weight(1f)
                            .height(
                                when (textType) {
                                    TypingTextFieldType.Basic -> fieldHeight
                                    TypingTextFieldType.LongSentence -> 96.5.dp
                                }
                            )
                    },
                    textStyle = textStyle,
                    singleLine = if (textType == TypingTextFieldType.LongSentence) false else isSingleLine,
                    minLines = if (isSingleLine) 1 else 3,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    cursorBrush = SolidColor(value = cursorColor ?: currentColorState.value),
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
                                style = Body1.merge(color = hintTextColor),
                                letterSpacing = (-0.25).sp
                            )
                        },
                        contentPadding = contentPadding,
                        trailingIcon = trailingIconContent
                    )
                }
            }

            if (textType == TypingTextFieldType.LongSentence) {
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
        if (textType == TypingTextFieldType.Basic && isError) {
            Spacer(Modifier.height(Space8))
            errorMessageContent()
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
        contentPadding = PaddingValues(
            vertical = 6.dp,
            horizontal = 10.dp
        ),
        leadingIconContent = {
            Box(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "bottom icon",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    )
}

@Preview
@Composable
fun TypingTextField4Preview() {
    TypingTextField(
        TypingTextFieldType.LongSentence,
        text = "무릇은 경조사비 관리앱으로 사용자가 다양한 개인적인 축하 상황에 대해 금전적 기여를 쉽게 할 수 있게 돕는 모바일 애플리케이션입니다",
        hintText = "",
        onValueChange = {}
    )
}
