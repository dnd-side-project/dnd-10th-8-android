package ac.dnd.bookkeeping.android.presentation.ui.main.registration.naming

import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Body1
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray400
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray600
import ac.dnd.bookkeeping.android.presentation.common.theme.Negative
import ac.dnd.bookkeeping.android.presentation.common.theme.Space12
import ac.dnd.bookkeeping.android.presentation.common.theme.Space20
import ac.dnd.bookkeeping.android.presentation.common.theme.Space32
import ac.dnd.bookkeeping.android.presentation.common.theme.Space4
import ac.dnd.bookkeeping.android.presentation.common.theme.Space48
import ac.dnd.bookkeeping.android.presentation.common.theme.Space8
import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.common.util.expansion.addFocusCleaner
import ac.dnd.bookkeeping.android.presentation.common.view.CustomTextField
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButton
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonProperties
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonSize
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonType
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.registration.collecting.RegistrationCollectingConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.registration.naming.component.ErrorUserNamingComponent
import ac.dnd.bookkeeping.android.presentation.ui.main.registration.naming.component.RegistraionUserDate
import ac.dnd.bookkeeping.android.presentation.ui.main.rememberApplicationState
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineExceptionHandler

@Composable
fun RegistrationNamingScreen(
    appState: ApplicationState,
    model: RegistrationNamingModel,
    event: EventFlow<RegistrationNamingEvent>,
    intent: (RegistrationNamingIntent) -> Unit,
    handler: CoroutineExceptionHandler
) {
    val focusManager = LocalFocusManager.current
    var userNameText by remember { mutableStateOf(TextFieldValue("")) }
    var userYearText by remember { mutableStateOf(TextFieldValue("")) }
    var userMonthText by remember { mutableStateOf(TextFieldValue("")) }
    var userDayText by remember { mutableStateOf(TextFieldValue("")) }
    var isUserMale by remember { mutableStateOf(true) }
    val userNameColorState = animateColorAsState(
        targetValue = when (model.namingErrorType) {
            RegistrationNamingErrorType.Init -> Gray400
            is RegistrationNamingErrorType.InValid -> Negative
        },
        label = "userName color type"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager)
            .background(color = Color.White),
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .height(80.dp)
                .padding(
                    start = 10.dp,
                    bottom = 12.67.dp
                ),
            contentAlignment = Alignment.BottomStart
        ) {
            Image(
                painter = painterResource(R.drawable.ic_chevron_left),
                modifier = Modifier
                    .size(24.dp)
                    .clickable {

                    },
                contentDescription = "back press"
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(
                    start = Space20,
                    end = Space20,
                    bottom = 220.dp
                )
        ) {
            Text(
                text = "닉네임",
                style = Body1.merge(
                    color = Gray600,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(Modifier.height(Space12))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Space12)
            ) {
                CustomTextField(
                    text = userNameText.text,
                    onTextChange = {
                        userNameText = TextFieldValue(it)
                    },
                    height = Space48,
                    elevation = 0.dp,
                    cornerBorder = BorderStroke(
                        width = 1.dp,
                        color = userNameColorState.value
                    ),
                    modifier = Modifier.background(Color.White),
                    contentInnerPadding = PaddingValues(horizontal = 16.dp),
                    trailingIconContent = {
                        if (userNameText.text.isNotEmpty()) {
                            IconButton(
                                onClick = { userNameText = TextFieldValue() }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_close),
                                    contentDescription = "close icon",
                                )
                            }
                        }
                    },
                    hintTextContent = {
                        Text(
                            text = stringResource(R.string.input_user_name),
                            style = Body1.merge(color = Gray600)
                        )
                    }
                )
                ConfirmButton(
                    modifier = Modifier.wrapContentWidth(),
                    properties = ConfirmButtonProperties(
                        size = ConfirmButtonSize.Large,
                        type = ConfirmButtonType.Secondary
                    ),
                    onClick = {
                        //TODO : check user
                    },
                    content = { style ->
                        Text(
                            text = stringResource(R.string.confirm_button_check_duplication),
                            style = style.merge(color = Gray600)
                        )
                    }
                )
            }
            Spacer(Modifier.height(Space8))

            ErrorUserNamingComponent(model.namingErrorType)
            Spacer(Modifier.height(Space32))

            RegistraionUserDate(
                focusManager = focusManager,
                userYearText = userYearText,
                userMonthText = userMonthText,
                userDayText = userDayText,
                onUserYearTextChange = { newText ->
                    userYearText = newText
                },
                onUserMonthTextChange = { newText ->
                    userMonthText = newText
                },
            ) { newText ->
                userDayText = newText
            }

            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "성별",
                style = Body1.merge(
                    color = Gray600,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row {
                ConfirmButton(
                    modifier = Modifier.weight(1f),
                    properties = ConfirmButtonProperties(
                        size = ConfirmButtonSize.Large,
                        type = ConfirmButtonType.Outline
                    ),
                    content = { style ->
                        Text(text = "남자", style = style)
                    },
                    onClick = {
                        isUserMale = true
                    },
                )
                Spacer(modifier = Modifier.width(12.dp))
                ConfirmButton(
                    modifier = Modifier.weight(1f),
                    properties = ConfirmButtonProperties(
                        size = ConfirmButtonSize.Large,
                        type = ConfirmButtonType.Outline
                    ),
                    content = { style ->
                        Text(text = "여자", style = style)
                    },
                    onClick = {
                        isUserMale = false
                    }
                )
            }
        }

        ConfirmButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp,
                    vertical = 12.dp
                ),
            properties = ConfirmButtonProperties(
                size = ConfirmButtonSize.Xlarge,
                type = ConfirmButtonType.Primary
            ),
            content = { style ->
                Text(text = "다음", style = style)
            }
        )

    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is RegistrationNamingEvent.GoToNextStep -> {
                    appState.navController.navigate(RegistrationCollectingConstant.ROUTE)
                }
            }
        }
    }
}

@Preview
@Composable
fun RegistrationNamingScreenPreview() {
    RegistrationNamingScreen(
        appState = rememberApplicationState(),
        model = RegistrationNamingModel(
            state = RegistrationNamingState.Init,
            namingErrorType = RegistrationNamingErrorType.Init
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
