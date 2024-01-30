package ac.dnd.bookkeeping.android.presentation.ui.main.registration.main

import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Body1
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray000
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray200
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray400
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray500
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray600
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline3
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary1
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary4
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary5
import ac.dnd.bookkeeping.android.presentation.common.theme.Shapes
import ac.dnd.bookkeeping.android.presentation.common.theme.Space12
import ac.dnd.bookkeeping.android.presentation.common.theme.Space20
import ac.dnd.bookkeeping.android.presentation.common.theme.Space32
import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.common.util.expansion.addFocusCleaner
import ac.dnd.bookkeeping.android.presentation.common.view.DialogScreen
import ac.dnd.bookkeeping.android.presentation.common.view.textfield.TypingTextField
import ac.dnd.bookkeeping.android.presentation.common.view.textfield.TypingTextFieldType
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.HomeConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.login.LoginConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.registration.main.component.ErrorUserNamingComponent
import ac.dnd.bookkeeping.android.presentation.ui.main.registration.main.component.RegistraionUserDate
import ac.dnd.bookkeeping.android.presentation.ui.main.registration.main.type.RegistrationMainNamingErrorType
import ac.dnd.bookkeeping.android.presentation.ui.main.registration.main.type.UserGender
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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

@Composable
fun RegistrationNamingScreen(
    appState: ApplicationState,
    model: RegistrationMainModel,
    event: EventFlow<RegistrationMainEvent>,
    intent: (RegistrationMainIntent) -> Unit,
    handler: CoroutineExceptionHandler
) {
    val scope = rememberCoroutineScope() + handler
    val focusManager = LocalFocusManager.current
    var isDialogShowing by remember { mutableStateOf(false) }
    var isUserNameInValid by remember { mutableStateOf(false) }
    var userNameText by remember { mutableStateOf("") }
    var userYearText by remember { mutableStateOf("") }
    var userMonthText by remember { mutableStateOf("") }
    var userDayText by remember { mutableStateOf("") }
    var userGender by remember { mutableStateOf(UserGender.NORMAL) }
    var buttonClickState by remember { mutableStateOf(false) }
    var checkNonDuplicationState by remember { mutableStateOf(false) }
    val registrationButtonColorState = animateColorAsState(
        targetValue = checkColorState(
            buttonClickState = buttonClickState,
            nameValid = checkNonDuplicationState && !isUserNameInValid,
            gender = userGender,
            year = userYearText,
            month = userMonthText,
            day = userDayText
        ),
        label = "registration button state"
    )

    fun navigateToHome() {
        appState.navController.navigate(HomeConstant.ROUTE) {
            popUpTo(RegistrationMainConstant.CONTAIN_USER_MODEL) {
                inclusive = true
            }
        }
    }

    fun applyValidation(event: RegistrationMainEvent.Check) {
        when (event) {
            is RegistrationMainEvent.Check.Valid -> {
                checkNonDuplicationState = true
            }

            is RegistrationMainEvent.Check.Invalid -> {}
        }
    }

    fun submit(event: RegistrationMainEvent.Submit) {
        when (event) {
            is RegistrationMainEvent.Submit.Success -> {
                navigateToHome()
            }

            is RegistrationMainEvent.Submit.Error -> {
                isDialogShowing = true
            }

            is RegistrationMainEvent.Submit.Failure -> {
                isDialogShowing = true
            }
        }
    }

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
                Box(modifier = Modifier.weight(1f)) {
                    TypingTextField(
                        textType = TypingTextFieldType.Basic,
                        text = userNameText,
                        hintText = "닉네임 입력 (15자 이내)",
                        onValueChange = {
                            userNameText = it
                            isUserNameInValid = userNameText.length >= 15
                        },
                        isEnabled = !checkNonDuplicationState,
                        isError = isUserNameInValid,
                        modifier = Modifier,
                        trailingIconContent = {
                            if (userNameText.isNotEmpty()) {
                                if (isUserNameInValid) {
                                    Image(
                                        painter = painterResource(R.drawable.ic_warning),
                                        contentDescription = null,
                                        modifier = Modifier.size(Space20)
                                    )
                                } else if (checkNonDuplicationState) {
                                    Image(
                                        painter = painterResource(R.drawable.ic_check_circle),
                                        contentDescription = null,
                                        modifier = Modifier.size(Space20)
                                    )
                                } else {
                                    IconButton(
                                        onClick = {
                                            userNameText = ""
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
                        },
                        errorMessageContent = {
                            if (isUserNameInValid) {
                                ErrorUserNamingComponent(RegistrationMainNamingErrorType.InValid.NumberOutOfRange)
                            } else {
                                ErrorUserNamingComponent(model.namingErrorType)
                            }
                        }
                    )
                }
                Button(
                    modifier = Modifier
                        .height(48.dp)
                        .wrapContentWidth(),
                    shape = Shapes.large,
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = if (checkNonDuplicationState) Gray500 else Gray200
                    ),
                    contentPadding = PaddingValues(horizontal = 13.dp),
                    enabled = !isUserNameInValid && !checkNonDuplicationState,
                    onClick = {
                        intent(RegistrationMainIntent.OnCheckUserName(userNameText))
                    },
                    elevation = null
                ) {
                    Text(
                        text = if (checkNonDuplicationState) "확인 완료" else "중복 확인",
                        style = Body1.merge(
                            color = if (checkNonDuplicationState) Gray000 else Gray600,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
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
                onUserDayTextChange = { newText ->
                    userDayText = newText
                }
            )

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
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = Shapes.large,
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = if (userGender == UserGender.MALE) Primary1 else Gray000
                    ),
                    border = BorderStroke(
                        color = if (userGender == UserGender.MALE) Primary4 else Gray400,
                        width = 1.dp,
                    ),
                    onClick = {
                        userGender = UserGender.MALE
                    },
                    elevation = null
                ) {
                    Text(
                        text = "남자",
                        style = Body1.merge(if (userGender == UserGender.MALE) Primary4 else Gray600)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Button(
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = Shapes.large,
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = if (userGender == UserGender.FEMALE) Primary1 else Gray000
                    ),
                    border = BorderStroke(
                        color = if (userGender == UserGender.FEMALE) Primary4 else Gray400,
                        width = 1.dp,
                    ),
                    onClick = {
                        userGender = UserGender.FEMALE
                    },
                    elevation = null
                ) {
                    Text(
                        text = "여자",
                        style = Body1.merge(if (userGender == UserGender.FEMALE) Primary4 else Gray600)
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(
                    horizontal = 20.dp,
                    vertical = 12.dp
                )
        ) {
            Button(
                modifier = Modifier
                    .height(52.dp)
                    .fillMaxWidth(),
                shape = Shapes.large,
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = registrationButtonColorState.value
                ),
                border = BorderStroke(
                    color = if (userGender == UserGender.MALE) Primary4 else Gray400,
                    width = 1.dp,
                ),
                enabled = registrationButtonColorState.value == Primary4,
                onClick = {
                    scope.launch {
                        buttonClickState = true
                        delay(200L)
                        intent(
                            RegistrationMainIntent.OnClickSubmit(
                                nickName = userNameText,
                                gender = if (userGender == UserGender.MALE) "male" else "female",
                                birth = listOf(
                                    userYearText,
                                    userMonthText,
                                    userDayText
                                ).joinToString("-")
                            )
                        )
                    }
                },
                elevation = null
            ) {
                Text(
                    text = "시작하기",
                    style = Headline3
                        .merge(
                            color = Gray000,
                            fontWeight = FontWeight.SemiBold
                        )
                )
            }
        }
    }

    DialogScreen(
        isShowing = isDialogShowing,
        title = "회원 가입에 실패하였습니다.",
        onDismissRequest = {
            isDialogShowing = false
        }
    )

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is RegistrationMainEvent.Check -> applyValidation(event)
                is RegistrationMainEvent.Submit -> submit(event)
            }
        }
    }
}

private fun checkColorState(
    buttonClickState: Boolean,
    nameValid: Boolean,
    gender: UserGender,
    year: String,
    month: String,
    day: String
): Color =
    if (buttonClickState) Primary5
    else if (nameValid && gender != UserGender.NORMAL && year.length == 4 && month.length == 2 && day.length == 2) Primary4
    else Gray400

@Preview
@Composable
fun RegistrationNamingScreenPreview() {
    RegistrationNamingScreen(
        appState = rememberApplicationState(),
        model = RegistrationMainModel(
            state = RegistrationMainState.Init,
            namingErrorType = RegistrationMainNamingErrorType.Init
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
