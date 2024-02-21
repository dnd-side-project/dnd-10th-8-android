package ac.dnd.mour.android.presentation.ui.main.home.mypage

import ac.dnd.mour.android.domain.model.member.Profile
import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.theme.Body0
import ac.dnd.mour.android.presentation.common.theme.Body1
import ac.dnd.mour.android.presentation.common.theme.Gray000
import ac.dnd.mour.android.presentation.common.theme.Gray100
import ac.dnd.mour.android.presentation.common.theme.Gray300
import ac.dnd.mour.android.presentation.common.theme.Gray700
import ac.dnd.mour.android.presentation.common.theme.Gray800
import ac.dnd.mour.android.presentation.common.theme.Gray900
import ac.dnd.mour.android.presentation.common.theme.Headline1
import ac.dnd.mour.android.presentation.common.theme.Headline2
import ac.dnd.mour.android.presentation.common.theme.Primary1
import ac.dnd.mour.android.presentation.common.theme.Primary4
import ac.dnd.mour.android.presentation.common.theme.Shapes
import ac.dnd.mour.android.presentation.common.util.ErrorObserver
import ac.dnd.mour.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.mour.android.common.coroutine.event.EventFlow
import ac.dnd.mour.android.common.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.common.coroutine.event.eventObserve
import ac.dnd.mour.android.presentation.common.util.makeRoute
import ac.dnd.mour.android.presentation.common.view.DialogScreen
import ac.dnd.mour.android.presentation.model.mypage.ProfileModel
import ac.dnd.mour.android.presentation.model.mypage.toUiModel
import ac.dnd.mour.android.presentation.ui.main.ApplicationState
import ac.dnd.mour.android.presentation.ui.main.home.mypage.profile.MyPageProfileConstant
import ac.dnd.mour.android.presentation.ui.main.home.mypage.setting.withdraw.MyPageSettingWithdrawConstant
import ac.dnd.mour.android.presentation.ui.main.login.main.LoginMainConstant
import ac.dnd.mour.android.presentation.ui.main.rememberApplicationState
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.datetime.LocalDate

@Composable
fun MyPageScreen(
    appState: ApplicationState,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val model: MyPageModel = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()
        val profile by viewModel.profile.collectAsStateWithLifecycle()
        MyPageModel(
            state = state,
            profile = profile,
        )
    }
    ErrorObserver(viewModel)

    MyPageScreen(
        appState = appState,
        model = model,
        event = viewModel.event,
        intent = viewModel::onIntent,
        handler = viewModel.handler
    )
}

@Composable
private fun MyPageScreen(
    appState: ApplicationState,
    model: MyPageModel,
    event: EventFlow<MyPageEvent>,
    intent: (MyPageIntent) -> Unit,
    handler: CoroutineExceptionHandler
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var isShowingLogoutDialog by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    fun navigateToLink() {
        val browserIntent =
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLSePnH7MAeuaPzsOp9WXIfgFmnxVelsHBixcc912bH5O7ze1MQ/viewform")
            )
        ContextCompat.startActivity(context, browserIntent, null)
    }

    fun navigateToEditProfile() {
        appState.navController.sendProfileModel(model.profile.toUiModel())
    }

    fun navigateToWithdraw() {
        val route = makeRoute(
            MyPageSettingWithdrawConstant.ROUTE,
            listOf(MyPageSettingWithdrawConstant.ROUTE_ARGUMENT_NAME to model.profile.nickname)
        )
        appState.navController.navigate(route)
    }

    fun logout(event: MyPageEvent.Logout) {
        when (event) {
            is MyPageEvent.Logout.Success -> {
                appState.navController.navigate(LoginMainConstant.ROUTE) {
                    popUpTo(MyPageConstant.ROUTE) {
                        inclusive = true
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray000)
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "마이페이지",
                style = Headline1.merge(
                    color = Gray900,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.align(Alignment.Center)
            )

            Image(
                painter = painterResource(R.drawable.ic_notification),
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
        Spacer(modifier = Modifier.height(43.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (model.profile.profileImageUrl.isEmpty()) {
                Image(
                    painter = painterResource(R.drawable.ic_default_user_image),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                )
            } else {
                AsyncImage(
                    model = model.profile.profileImageUrl,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = model.profile.nickname,
                style = Headline2.merge(
                    color = Color(0xFF484848),
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .clip(Shapes.medium)
                    .background(color = Primary1)
                    .clickable {
                        navigateToEditProfile()
                    }
                    .width(79.dp)
                    .height(34.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "프로필 수정",
                    style = Body1.merge(
                        color = Primary4,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .background(
                    color = Gray100,
                    shape = Shapes.medium
                )
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "생년월일",
                    style = Body1.merge(
                        color = Gray700,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                val dateText = listOf(
                    model.profile.birth.year,
                    model.profile.birth.monthNumber,
                    model.profile.birth.dayOfMonth
                ).joinToString("-")
                Text(
                    text = dateText,
                    style = Body1.merge(
                        color = Gray700,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "성별",
                    style = Body1.merge(
                        color = Gray700,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Text(
                    text = if (model.profile.gender == "male") "남자" else "여자",
                    style = Body1.merge(
                        color = Gray700,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier
                .height(64.dp)
                .clickable {
                    navigateToLink()
                }
                .padding(horizontal = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "1:1 문의하기",
                style = Body0.merge(
                    color = Gray800,
                    fontWeight = FontWeight.Normal
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(R.drawable.ic_chevron_right),
                contentDescription = null,
            )
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp),
            color = Gray300
        )

        Row(
            modifier = Modifier
                .height(64.dp)
                .padding(horizontal = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "버전",
                style = Body0.merge(
                    color = Gray800,
                    fontWeight = FontWeight.Normal
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            //TODO version
            Text(
                text = "v1.0",
                style = Body1.merge(
                    color = Gray900,
                    fontWeight = FontWeight.Medium
                )
            )
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp),
            color = Gray300
        )

        Row(
            modifier = Modifier
                .height(64.dp)
                .clickable {

                }
                .padding(horizontal = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "이용약관",
                style = Body0.merge(
                    color = Gray800,
                    fontWeight = FontWeight.Normal
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(R.drawable.ic_chevron_right),
                contentDescription = null,
            )
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp),
            color = Gray300
        )

        Row(
            modifier = Modifier
                .height(64.dp)
                .clickable {
                    isShowingLogoutDialog = true
                }
                .padding(horizontal = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "로그아웃",
                style = Body0.merge(
                    color = Gray800,
                    fontWeight = FontWeight.Normal
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(R.drawable.ic_chevron_right),
                contentDescription = null,
            )
        }

        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = "회원 탈퇴",
            style = Body1.merge(
                color = Gray700,
                fontWeight = FontWeight.Normal
            ),
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable {
                navigateToWithdraw()
            }
        )
        Spacer(modifier = Modifier.height(60.dp))
    }

    if (isShowingLogoutDialog) {
        DialogScreen(
            isCancelable = true,
            message = "로그아웃 하시겠어요?",
            confirmMessage = "확인",
            cancelMessage = "취소",
            onCancel = {
                isShowingLogoutDialog = false
            },
            onDismissRequest = {
                isShowingLogoutDialog = false
            },
            onConfirm = {
                isShowingLogoutDialog = false
                intent(MyPageIntent.OnLogout)
            }
        )
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is MyPageEvent.Logout -> logout(event)
            }
        }
    }
}

private fun NavHostController.sendProfileModel(profileModel: ProfileModel) {
    currentBackStackEntry?.savedStateHandle?.apply {
        set(
            key = MyPageProfileConstant.ROURE_ARGUMENT_USER_MODEL,
            value = profileModel
        )
    }
    navigate(MyPageProfileConstant.CONTAIN_USER_MODEL)
}

@Preview
@Composable
private fun MyPageScreenPreview() {
    MyPageScreen(
        appState = rememberApplicationState(),
        model = MyPageModel(
            state = MyPageState.Init,
            profile = Profile(
                id = 0,
                email = "",
                profileImageUrl = "",
                name = "",
                nickname = "dd",
                gender = "",
                birth = LocalDate(2000, 1, 1)
            )
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
