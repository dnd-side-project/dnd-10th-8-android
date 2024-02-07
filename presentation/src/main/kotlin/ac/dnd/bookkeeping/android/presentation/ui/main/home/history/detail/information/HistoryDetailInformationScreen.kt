package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.detail.information

import ac.dnd.bookkeeping.android.domain.model.feature.relation.RelationDetailGroup
import ac.dnd.bookkeeping.android.domain.model.feature.relation.RelationDetailWithUserInfo
import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Body1
import ac.dnd.bookkeeping.android.presentation.common.theme.Caption2
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray000
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray150
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray200
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray300
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray500
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray700
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray800
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline2
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline3
import ac.dnd.bookkeeping.android.presentation.common.theme.Shapes
import ac.dnd.bookkeeping.android.presentation.common.theme.Space20
import ac.dnd.bookkeeping.android.presentation.common.theme.Space8
import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.common.view.BottomSheetScreen
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButton
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonProperties
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonSize
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonType
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.rememberApplicationState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import kotlinx.coroutines.CoroutineExceptionHandler

@Composable
fun HistoryDetailInformationScreen(
    appState: ApplicationState,
    relationDetail: RelationDetailWithUserInfo,
    onDismissRequest: () -> Unit,
    onResult: () -> Unit,
    viewModel: HistoryDetailInformationViewModel = hiltViewModel()
) {
    val model: HistoryDetailInformationModel = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()
        HistoryDetailInformationModel(
            state = state,
            relationDetail = relationDetail
        )
    }
    ErrorObserver(viewModel)

    HistoryDetailInformationScreen(
        appState = appState,
        model = model,
        event = viewModel.event,
        intent = viewModel::onIntent,
        handler = viewModel.handler,
        onDismissRequest = onDismissRequest,
        onResult = onResult
    )
}

@Composable
private fun HistoryDetailInformationScreen(
    appState: ApplicationState,
    model: HistoryDetailInformationModel,
    event: EventFlow<HistoryDetailInformationEvent>,
    intent: (HistoryDetailInformationIntent) -> Unit,
    handler: CoroutineExceptionHandler,
    onDismissRequest: () -> Unit,
    onResult: () -> Unit,
) {
    BottomSheetScreen(
        onDismissRequest = onDismissRequest,
        properties = BottomSheetDialogProperties(
            behaviorProperties = BottomSheetBehaviorProperties(
                state = BottomSheetBehaviorProperties.State.Expanded,
                skipCollapsed = true
            ),
            dismissOnBackPress = false
        )
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .background(Gray000)
                .padding(horizontal = Space20),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(Space20))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Space8)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable {
                            onDismissRequest()
                        }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(80.dp)
                    .background(color = Gray200)
            ){
                AsyncImage(
                    model = model.relationDetail.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(7.dp))
            Row(verticalAlignment = Alignment.CenterVertically){
                Text(
                    text = model.relationDetail.name,
                    style = Headline3.merge(
                        color = Gray700,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = "•",
                    style = Headline3.merge(
                        color = Gray700,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = model.relationDetail.group.name,
                    style = Headline3.merge(
                        color = Gray700,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Spacer(modifier = Modifier.height(7.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .border(
                        shape = Shapes.medium,
                        width = 1.dp,
                        color = Gray300
                    )
                    .clickable {
                        onResult()
                    }
                    .padding(
                        horizontal = 8.dp,
                        vertical = 5.dp
                    )
            ){
                Image(
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text ="편집",
                    style = Caption2.merge(
                        color = Gray500,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(
                        color = Gray150,
                        shape = Shapes.large
                    )
            ){
                Text(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    text = model.relationDetail.memo,
                    minLines = 3,
                    style =Body1.merge(
                        color = Gray800,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->

        }
    }
}

@Preview(apiLevel = 33)
@Composable
private fun HistoryDetailInformationScreenPreview() {
    HistoryDetailInformationScreen(
        appState = rememberApplicationState(),
        model = HistoryDetailInformationModel(
            state = HistoryDetailInformationState.Init,
            relationDetail = RelationDetailWithUserInfo(
                id = 0L,
                name = "김진우",
                imageUrl = "",
                memo = "무르는 경사비 관리앱으로 사용자가 다양한 개인적인 축하 상황에 대해 금전적 기여를 쉽게 할 수 있게 돕는 모바일 애플리케이션입니다",
                group = RelationDetailGroup(
                    id = 0,
                    name = "친척"
                ),
                giveMoney = 1000L,
                takeMoney = 1000L
            )
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> },
        onDismissRequest = {},
        onResult = {}
    )
}
