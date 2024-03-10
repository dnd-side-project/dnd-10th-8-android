package ac.dnd.mour.android.presentation.ui.main.registration.main

import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.theme.Body1
import ac.dnd.mour.android.presentation.common.theme.Gray000
import ac.dnd.mour.android.presentation.common.theme.Gray200
import ac.dnd.mour.android.presentation.common.theme.Gray400
import ac.dnd.mour.android.presentation.common.theme.Gray500
import ac.dnd.mour.android.presentation.common.theme.Gray600
import ac.dnd.mour.android.presentation.common.theme.Gray700
import ac.dnd.mour.android.presentation.common.theme.Gray800
import ac.dnd.mour.android.presentation.common.theme.Gray900
import ac.dnd.mour.android.presentation.common.theme.Headline1
import ac.dnd.mour.android.presentation.common.theme.Headline3
import ac.dnd.mour.android.presentation.common.theme.Primary1
import ac.dnd.mour.android.presentation.common.theme.Primary4
import ac.dnd.mour.android.presentation.common.theme.Primary5
import ac.dnd.mour.android.presentation.common.theme.Shapes
import ac.dnd.mour.android.presentation.common.theme.Space12
import ac.dnd.mour.android.presentation.common.theme.Space20
import ac.dnd.mour.android.presentation.common.theme.Space32
import ac.dnd.mour.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.mour.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.mour.android.presentation.common.util.expansion.addFocusCleaner
import ac.dnd.mour.android.presentation.common.util.logevent.LogEventUtil
import ac.dnd.mour.android.presentation.common.util.logevent.viewLogEvent
import ac.dnd.mour.android.presentation.common.view.DialogScreen
import ac.dnd.mour.android.presentation.common.view.textfield.TypingTextField
import ac.dnd.mour.android.presentation.common.view.textfield.TypingTextFieldType
import ac.dnd.mour.android.presentation.ui.main.ApplicationState
import ac.dnd.mour.android.presentation.ui.main.home.HomeConstant
import ac.dnd.mour.android.presentation.ui.main.registration.main.component.ErrorUserNamingComponent
import ac.dnd.mour.android.presentation.ui.main.registration.main.component.RegistraionUserDate
import ac.dnd.mour.android.presentation.ui.main.registration.main.type.RegistrationMainNamingErrorType
import ac.dnd.mour.android.presentation.ui.main.registration.main.type.UserGender
import ac.dnd.mour.android.presentation.ui.main.rememberApplicationState
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
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
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
    LaunchedEffect(Unit){
        viewLogEvent(
            LogEventUtil.VIEW_REQUIRED_INFO,
            block = {

            }
        )
    }

    val scope = rememberCoroutineScope() + handler
    val focusManager = LocalFocusManager.current
    var isDialogShowing by remember { mutableStateOf(false) }
    var isUserNameInValid by remember { mutableStateOf(false) }
    var userNameText by remember { mutableStateOf("") }
    var userYearText by remember { mutableStateOf("") }
    var userMonthText by remember { mutableStateOf("") }
    var userDayText by remember { mutableStateOf("") }
    var userGender: UserGender? by remember { mutableStateOf(null) }
    var buttonClickState by remember { mutableStateOf(false) }
    var checkNonDuplicationState by remember { mutableStateOf(false) }
    var isSelectedFirstAgreeButton by remember { mutableStateOf(false) }
    var isSelectedSecondAgreeButton by remember { mutableStateOf(false) }
    var isSelectedThirdAgreeButton by remember { mutableStateOf(false) }
    val registrationButtonColorState = animateColorAsState(
        targetValue = checkColorState(
            buttonClickState = buttonClickState,
            nameValid = checkNonDuplicationState && !isUserNameInValid,
            gender = userGender,
            year = userYearText,
            month = userMonthText,
            day = userDayText,
            selectedAllAgree = isSelectedFirstAgreeButton && isSelectedSecondAgreeButton && isSelectedThirdAgreeButton
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .height(56.dp)
                .padding(horizontal = 20.dp)
                .padding(
                    bottom = 12.67.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_chevron_left),
                modifier = Modifier
                    .size(24.dp)
                    .clickable {

                    },
                contentDescription = "back press"
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "개인정보 입력",
                style = Headline1.merge(
                    color = Gray900,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 90.dp)
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = "닉네임",
                style = Body1.merge(
                    color = Gray700,
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
                    color = Gray700,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = Shapes.medium,
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = if (userGender == UserGender.Male) Primary1 else Gray000
                    ),
                    border = BorderStroke(
                        color = if (userGender == UserGender.Male) Primary4 else Gray400,
                        width = 1.dp,
                    ),
                    onClick = {
                        userGender = UserGender.Male
                    },
                    elevation = null
                ) {
                    Text(
                        text = "남자",
                        style = Body1.merge(if (userGender == UserGender.Male) Primary4 else Gray600)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = Shapes.medium,
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = if (userGender == UserGender.Female) Primary1 else Gray000
                    ),
                    border = BorderStroke(
                        color = if (userGender == UserGender.Female) Primary4 else Gray400,
                        width = 1.dp,
                    ),
                    onClick = {
                        userGender = UserGender.Female
                    },
                    elevation = null
                ) {
                    Text(
                        text = "여자",
                        style = Body1.merge(if (userGender == UserGender.Female) Primary4 else Gray600)
                    )
                }
            }
            Spacer(modifier = Modifier.height(40.dp))


            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = isSelectedFirstAgreeButton,
                    onClick = {
                        isSelectedFirstAgreeButton = !isSelectedFirstAgreeButton
                    },
                    modifier = Modifier.size(20.dp),
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Primary4,
                        unselectedColor = Gray500
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(color = Primary4)
                        ) {
                            append("[필수]")
                        }
                        append(" 이용약관 동의")
                    },
                    style = Body1.merge(
                        color = Gray800,
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(R.drawable.ic_chevron_right),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(Gray600)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = isSelectedSecondAgreeButton,
                    onClick = {
                        isSelectedSecondAgreeButton = !isSelectedSecondAgreeButton
                    },
                    modifier = Modifier.size(20.dp),
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Primary4,
                        unselectedColor = Gray500
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = buildAnnotatedString {

                        withStyle(
                            SpanStyle(color = Primary4)
                        ) {
                            append("[필수]")
                        }
                        append(" 개인정보 수집 및 이용동의")
                    },
                    style = Body1.merge(
                        color = Gray800,
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(R.drawable.ic_chevron_right),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(Gray600)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = isSelectedThirdAgreeButton,
                    onClick = {
                        isSelectedThirdAgreeButton = !isSelectedThirdAgreeButton
                    },
                    modifier = Modifier.size(20.dp),
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Primary4,
                        unselectedColor = Gray500
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = buildAnnotatedString {

                        withStyle(
                            SpanStyle(color = Primary4)
                        ) {
                            append("[필수]")
                        }
                        append(" 만 14세 이상입니다.")
                    },
                    style = Body1.merge(
                        color = Gray800,
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(R.drawable.ic_chevron_right),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(Gray600)
                )
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
                enabled = registrationButtonColorState.value == Primary4,
                onClick = {
                    scope.launch {
                        buttonClickState = true
                        delay(200L)
                        intent(
                            RegistrationMainIntent.OnClickSubmit(
                                nickName = userNameText,
                                gender = if (userGender == UserGender.Male) "male" else "female",
                                birth = listOf(
                                    userYearText,
                                    userMonthText,
                                    userDayText
                                ).joinToString("-")
                            )
                        )
                        viewLogEvent(
                            LogEventUtil.CLICK_START_REQUIRED_INFO,
                            block = {

                            }
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

    if (isDialogShowing) {
        DialogScreen(
            title = "회원 가입에 실패하였습니다.",
            onDismissRequest = {
                isDialogShowing = false
            }
        )
    }

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
    gender: UserGender?,
    year: String,
    month: String,
    day: String,
    selectedAllAgree: Boolean
): Color =
    if (buttonClickState) Primary5
    else if (nameValid && gender != null && year.length == 4 && month.length == 2 && day.length == 2 && selectedAllAgree) Primary4
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
