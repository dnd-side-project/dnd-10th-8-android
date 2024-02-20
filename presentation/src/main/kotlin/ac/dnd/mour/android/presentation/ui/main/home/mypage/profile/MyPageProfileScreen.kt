package ac.dnd.mour.android.presentation.ui.main.home.mypage.profile

import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.theme.Body1
import ac.dnd.mour.android.presentation.common.theme.Gray000
import ac.dnd.mour.android.presentation.common.theme.Gray400
import ac.dnd.mour.android.presentation.common.theme.Gray600
import ac.dnd.mour.android.presentation.common.theme.Gray700
import ac.dnd.mour.android.presentation.common.theme.Gray900
import ac.dnd.mour.android.presentation.common.theme.Headline1
import ac.dnd.mour.android.presentation.common.theme.Headline3
import ac.dnd.mour.android.presentation.common.theme.Negative
import ac.dnd.mour.android.presentation.common.theme.Primary1
import ac.dnd.mour.android.presentation.common.theme.Primary4
import ac.dnd.mour.android.presentation.common.theme.Shapes
import ac.dnd.mour.android.presentation.common.theme.Space12
import ac.dnd.mour.android.presentation.common.theme.Space20
import ac.dnd.mour.android.presentation.common.theme.Space32
import ac.dnd.mour.android.presentation.common.theme.Space4
import ac.dnd.mour.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.mour.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.mour.android.presentation.common.util.expansion.addFocusCleaner
import ac.dnd.mour.android.presentation.common.view.SnackBarScreen
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButton
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonProperties
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonSize
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonType
import ac.dnd.mour.android.presentation.common.view.textfield.TypingTextField
import ac.dnd.mour.android.presentation.common.view.textfield.TypingTextFieldType
import ac.dnd.mour.android.presentation.model.mypage.ProfileModel
import ac.dnd.mour.android.presentation.ui.main.ApplicationState
import ac.dnd.mour.android.presentation.ui.main.common.gallery.GalleryScreen
import ac.dnd.mour.android.presentation.ui.main.rememberApplicationState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

@Composable
fun MyPageProfileScreen(
    appState: ApplicationState,
    model: MyPageProfileModel,
    event: EventFlow<MyPageProfileEvent>,
    intent: (MyPageProfileIntent) -> Unit,
    handler: CoroutineExceptionHandler
) {

    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    var isShowingGalleryView by remember { mutableStateOf(false) }
    var isUserNameInValid by remember { mutableStateOf(false) }
    var userNameText by remember { mutableStateOf(model.profile.name) }
    var userYearText by remember { mutableStateOf(model.profile.birth.year.toString()) }
    var userMonthText by remember { mutableStateOf(model.profile.birth.monthNumber.toString()) }
    var userDayText by remember { mutableStateOf(model.profile.birth.dayOfMonth.toString()) }
    var userGender by remember { mutableStateOf(if (model.profile.gender == "male") "남자" else "여자") }
    var currentImageUrl by remember { mutableStateOf(model.profile.profileImageUrl) }
    var currentImageName by remember { mutableStateOf("") }
    var isShowingEditSnackBar by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray000)
            .addFocusCleaner(focusManager)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .verticalScroll(scrollState)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .clickable {
                            appState.navController.popBackStack()
                        },
                    painter = painterResource(R.drawable.ic_chevron_left),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "프로필 수정",
                    style = Headline1.merge(
                        color = Gray900,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clickable {
                            isShowingGalleryView = true
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .aspectRatio(1f)
                            .clip(CircleShape)
                            .background(Gray400)
                    ) {
                        if (currentImageUrl.isEmpty()) {
                            Image(
                                painter = painterResource(R.drawable.ic_default_user_image),
                                contentDescription = null
                            )
                        } else {
                            AsyncImage(
                                model = currentImageUrl,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    Image(
                        painter = painterResource(R.drawable.ic_camera),
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )
                }
            }
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                text = "이름",
                style = Body1.merge(
                    color = Gray700,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(Modifier.height(6.dp))
            TypingTextField(
                textType = TypingTextFieldType.Basic,
                text = userNameText,
                hintText = "닉네임 입력 (15자 이내)",
                onValueChange = {
                    userNameText = it
                    isUserNameInValid = userNameText.length >= 15
                },
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(R.drawable.ic_alert_triangle),
                                contentDescription = null
                            )
                            Spacer(Modifier.width(Space4))
                            Text(
                                text = "15자 이내로 입력 해주세요.",
                                style = Body1.merge(color = Negative)
                            )
                        }
                    }
                }
            )
            Spacer(Modifier.height(Space32))

            UserDate(
                focusManager = focusManager,
                userYearText = userYearText,
                userMonthText = userMonthText,
                userDayText = userDayText,
                onUserYearTextChange = { newText ->
                    userYearText = newText
                },
                onUserMonthTextChange = { newText ->
                    if (newText.toInt() in 1..12) {
                        userMonthText = newText
                    }
                },
                onUserDayTextChange = { newText ->
                    if (newText.toInt() in 1..31) {
                        userDayText = newText
                    }
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
                    shape = Shapes.large,
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = if (userGender == "남자") Primary1 else Gray000
                    ),
                    border = BorderStroke(
                        color = if (userGender == "남자") Primary4 else Gray400,
                        width = 1.dp,
                    ),
                    onClick = {
                        userGender = "남자"
                    },
                    elevation = null
                ) {
                    Text(
                        text = "남자",
                        style = Body1.merge(if (userGender == "남자") Primary4 else Gray600)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = Shapes.large,
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = if (userGender != "남자") Primary1 else Gray000
                    ),
                    border = BorderStroke(
                        color = if (userGender != "남자") Primary4 else Gray400,
                        width = 1.dp,
                    ),
                    onClick = {
                        userGender = "여자"
                    },
                    elevation = null
                ) {
                    Text(
                        text = "여자",
                        style = Body1.merge(if (userGender != "남자") Primary4 else Gray600)
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(52.dp)
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(
                    vertical = 12.dp,
                    horizontal = 20.dp
                )
        ) {
            ConfirmButton(
                modifier = Modifier.fillMaxWidth(),
                properties = ConfirmButtonProperties(
                    size = ConfirmButtonSize.Xlarge,
                    type = ConfirmButtonType.Primary
                ),
                onClick = {
                    if (!isUserNameInValid && userNameText.isNotEmpty()) {
                        intent(
                            MyPageProfileIntent.OnEdit(
                                ProfileModel(
                                    id = model.profile.id,
                                    email = model.profile.email,
                                    profileImageUrl = currentImageUrl,
                                    name = model.profile.name,
                                    nickname = userNameText,
                                    gender = if (userGender == "남자") "male" else "female",
                                    birth = LocalDate(
                                        userYearText.toInt(),
                                        userMonthText.toInt(),
                                        userDayText.toInt()
                                    )
                                ),
                                imageName = currentImageName
                            )
                        )
                    }
                }
            ) {
                Text(
                    text = "저장하기",
                    style = Headline3.merge(
                        color = Gray000,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }

        if (isShowingEditSnackBar) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 71.dp)
            ) {
                SnackBarScreen("저장이 완료되었습니다.")
            }
        }
    }

    if (isShowingGalleryView) {
        GalleryScreen(
            appState = appState,
            onDismissRequest = {
                isShowingGalleryView = false
            },
            onResult = { galleryImage ->
                currentImageUrl = galleryImage.filePath
                currentImageName = galleryImage.name
            }
        )
    }


    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is MyPageProfileEvent.Edit -> {
                    scope.launch {
                        isShowingEditSnackBar = true
                        delay(1000L)
                        isShowingEditSnackBar = false
                    }
                }
            }
        }
    }
}

@Composable
private fun UserDate(
    focusManager: FocusManager,
    userYearText: String,
    userMonthText: String,
    userDayText: String,
    onUserYearTextChange: (String) -> Unit,
    onUserMonthTextChange: (String) -> Unit,
    onUserDayTextChange: (String) -> Unit,
) {
    Text(
        text = "생년월일",
        style = Body1.merge(
            color = Gray700,
            fontWeight = FontWeight.SemiBold
        )
    )
    Spacer(Modifier.height(Space12))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(modifier = Modifier.weight(114f)) {
            TypingTextField(
                textType = TypingTextFieldType.Basic,
                text = userYearText,
                hintText = "YYYY",
                onValueChange = {
                    if (userYearText.length < 4) {
                        onUserYearTextChange(it)
                        if (it.length == 4) focusManager.moveFocus(FocusDirection.Right)
                    }
                },
                trailingIconContent = {
                    if (userYearText.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                onUserYearTextChange("")
                            }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_close_circle),
                                contentDescription = "close icon",
                                modifier = Modifier.size(Space20)
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        Spacer(Modifier.weight(10f))
        Box(modifier = Modifier.weight(90f)) {
            TypingTextField(
                textType = TypingTextFieldType.Basic,
                text = userMonthText,
                hintText = "MM",
                onValueChange = {
                    if (userMonthText.length < 2) {
                        onUserMonthTextChange(it)
                        if (it.length == 2) focusManager.moveFocus(FocusDirection.Right)
                    }
                },
                trailingIconContent = {
                    if (userMonthText.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                onUserMonthTextChange("")
                            }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_close_circle),
                                contentDescription = "close icon",
                                modifier = Modifier.size(Space20)
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        Spacer(Modifier.weight(10f))
        Box(modifier = Modifier.weight(90f)) {
            TypingTextField(
                textType = TypingTextFieldType.Basic,
                text = userDayText,
                hintText = "DD",
                onValueChange = {
                    if (userDayText.length < 2) onUserDayTextChange(it)
                },
                trailingIconContent = {
                    if (userDayText.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                onUserDayTextChange("")
                            }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_close_circle),
                                contentDescription = "close icon",
                                modifier = Modifier.size(Space20)
                            )

                        }
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }
}


@Preview
@Composable
fun MyPageProfileScreenPreview() {
    MyPageProfileScreen(
        appState = rememberApplicationState(),
        model = MyPageProfileModel(
            state = MyPageProfileState.Init,
            profile = ProfileModel(
                id = 0L,
                email = "",
                profileImageUrl = "",
                name = "",
                nickname = "",
                gender = "male",
                birth = LocalDate(2020, 1, 1)
            )
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
