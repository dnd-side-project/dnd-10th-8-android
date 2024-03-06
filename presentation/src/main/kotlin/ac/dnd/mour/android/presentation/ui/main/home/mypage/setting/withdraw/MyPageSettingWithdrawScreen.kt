package ac.dnd.mour.android.presentation.ui.main.home.mypage.setting.withdraw

import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.theme.Body1
import ac.dnd.mour.android.presentation.common.theme.Gray000
import ac.dnd.mour.android.presentation.common.theme.Gray800
import ac.dnd.mour.android.presentation.common.theme.Gray900
import ac.dnd.mour.android.presentation.common.theme.Headline1
import ac.dnd.mour.android.presentation.common.theme.Headline3
import ac.dnd.mour.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.mour.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButton
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonProperties
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonSize
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonType
import ac.dnd.mour.android.presentation.ui.main.ApplicationState
import ac.dnd.mour.android.presentation.ui.main.login.main.LoginMainConstant
import ac.dnd.mour.android.presentation.ui.main.rememberApplicationState
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineExceptionHandler

@Composable
fun MyPageSettingWithdrawScreen(
    appState: ApplicationState,
    model: MyPageSettingWithdrawModel,
    event: EventFlow<MyPageSettingWithdrawEvent>,
    intent: (MyPageSettingWithdrawIntent) -> Unit,
    handler: CoroutineExceptionHandler
) {
    val scope = rememberCoroutineScope()
    var isCheckAgree by remember { mutableStateOf(false) }

    fun withdraw(event: MyPageSettingWithdrawEvent.Withdraw) {
        when (event) {
            is MyPageSettingWithdrawEvent.Withdraw.Success -> {
                appState.navController.navigate(LoginMainConstant.ROUTE) {
                    popUpTo(MyPageSettingWithdrawConstant.ROUTE) {
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
            .padding(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_chevron_left),
                contentDescription = null,
                modifier = Modifier.clickable {
                    appState.navController.popBackStack()
                }
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "탈퇴하기",
                style = Headline1.merge(
                    color = Gray900,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }

        Spacer(modifier = Modifier.height(31.dp))
        Text(
            text = "${model.nickname}님, 정말로 탈퇴하시겠어요?",
            style = Headline3.merge(
                color = Gray900,
                fontWeight = FontWeight.SemiBold
            )
        )
        Spacer(modifier = Modifier.height(19.dp))
        Text(
            text = "회원 탈퇴를 신청하시기 전에 \n" +
                    "다음 안내사항을 반드시 읽어주세요.",
            style = Body1.merge(
                color = Gray800,
                fontWeight = FontWeight.SemiBold
            )
        )
        Spacer(modifier = Modifier.height(25.dp))
        Row {
            Text(
                text = "• ",
                style = Body1.merge(
                    color = Gray800,
                    fontWeight = FontWeight.Normal
                )
            )
            Text(
                text = "지금 탈퇴하시면 탈퇴 후 개인 정보, 마음 기록 내역, 통계 등의 데이터가 삭제되며, 복구할 수 없습니다.",
                style = Body1.merge(
                    color = Gray800,
                    fontWeight = FontWeight.Normal
                )
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                isCheckAgree = !isCheckAgree
            }
        ) {
            val imageResource =
                if (isCheckAgree) R.drawable.ic_check_circle else R.drawable.ic_check_circle_gray
            Image(
                painter = painterResource(imageResource),
                contentDescription = null,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "모든 정보를 삭제하는 것에 동의합니다.",
                style = Body1.merge(
                    color = Gray900,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            ConfirmButton(
                modifier = Modifier.fillMaxWidth(),
                   properties = ConfirmButtonProperties(
                    size = ConfirmButtonSize.Xlarge,
                    type = if (isCheckAgree) ConfirmButtonType.Primary else ConfirmButtonType.Secondary
                ),
                isEnabled = isCheckAgree,
                onClick = {
                    intent(MyPageSettingWithdrawIntent.OnWithdraw)
                }
            ) {
                Text(
                    text = "탈퇴하기",
                    style = Headline3.merge(
                        color = if (isCheckAgree) Gray000 else Gray800,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is MyPageSettingWithdrawEvent.Withdraw -> withdraw(event)
            }
        }
    }
}

@Preview
@Composable
fun MyPageSettingWithdrawScreenPreview() {
    MyPageSettingWithdrawScreen(
        appState = rememberApplicationState(),
        model = MyPageSettingWithdrawModel(
            state = MyPageSettingWithdrawState.Init,
            nickname = "초코송이"
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
