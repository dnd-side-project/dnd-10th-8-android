package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.group.get

import ac.dnd.bookkeeping.android.domain.model.feature.group.Group
import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Body0
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray000
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray200
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray500
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray600
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray700
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray800
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline2
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline3
import ac.dnd.bookkeeping.android.presentation.common.theme.Shapes
import ac.dnd.bookkeeping.android.presentation.common.theme.Space12
import ac.dnd.bookkeeping.android.presentation.common.theme.Space16
import ac.dnd.bookkeeping.android.presentation.common.theme.Space20
import ac.dnd.bookkeeping.android.presentation.common.theme.Space8
import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.common.view.BottomSheetScreen
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.common.group.get.type.DefaultGroupType
import ac.dnd.bookkeeping.android.presentation.ui.main.rememberApplicationState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import kotlinx.coroutines.CoroutineExceptionHandler

@Composable
fun GetGroupScreen(
    appState: ApplicationState,
    groups: List<Group>,
    onDismissRequest: () -> Unit,
    onResult: (Group) -> Unit,
    viewModel: GetGroupViewModel = hiltViewModel()
) {
    val model: GetGroupModel = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()

        GetGroupModel(
            state = state,
            groups = groups
        )
    }
    ErrorObserver(viewModel)

    GetGroupScreen(
        appState = appState,
        onDismissRequest = onDismissRequest,
        onResult = onResult,
        model = model,
        intent = viewModel::onIntent,
        event = viewModel.event,
        handler = viewModel.handler,
    )
}

@Composable
private fun GetGroupScreen(
    appState: ApplicationState,
    onDismissRequest: () -> Unit,
    onResult: (Group) -> Unit,
    model: GetGroupModel,
    intent: (GetGroupIntent) -> Unit,
    event: EventFlow<GetGroupEvent>,
    handler: CoroutineExceptionHandler
) {

    BottomSheetScreen(
        onDismissRequest = onDismissRequest,
        properties = BottomSheetDialogProperties(
            behaviorProperties = BottomSheetBehaviorProperties(
                state = BottomSheetBehaviorProperties.State.Expanded,
                skipCollapsed = true
            )
        )
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .background(Gray200)
                .padding(horizontal = Space20)
        ) {
            Spacer(modifier = Modifier.height(Space20))
            Box(
                modifier = Modifier
                    .padding(Space8)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
            Text(
                text = "전체 그룹 ${model.groups.size}",
                style = Headline2.merge(
                    color = Gray800,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.padding(vertical = Space8)
            )
            Spacer(modifier = Modifier.height(Space20))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(Space12)) {
                items(model.groups) { group ->
                    var isDropDownMenuExpanded by remember { mutableStateOf(false) }
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onResult(group)
                            }
                            .padding(
                                start = Space16,
                                end = Space12,
                                top = Space16,
                                bottom = Space16
                            )
                    ) {
                        Text(
                            text = group.name,
                            style = Headline3.merge(
                                color = Gray700,
                                fontWeight = FontWeight.SemiBold
                            ),
                            modifier = Modifier.align(Alignment.CenterStart)
                        )
                        if (DefaultGroupType.checkEditable(group.name)) {
                            Image(
                                painter = painterResource(R.drawable.ic_more_vertical),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(Gray500),
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .clickable {
                                        isDropDownMenuExpanded = true
                                    }
                            )
                            DropdownMenu(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .background(
                                        color = Gray000,
                                        shape = Shapes.medium
                                    ),
                                expanded = isDropDownMenuExpanded,
                                onDismissRequest = { isDropDownMenuExpanded = false }
                            ) {
                                Column(verticalArrangement = Arrangement.Center) {
                                    DropdownMenuItem(
                                        onClick = {
                                            // TODO 수정
                                            isDropDownMenuExpanded = false
                                        },
                                        contentPadding = PaddingValues(0.dp),
                                        modifier = Modifier
                                            .width(100.dp)
                                            .height(40.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .padding(
                                                    vertical = Space8,
                                                    horizontal = Space12
                                                )
                                                .fillMaxWidth()
                                        ) {
                                            Text(
                                                text = "수정",
                                                style = Body0.merge(
                                                    color = Gray700,
                                                    fontWeight = FontWeight.Normal
                                                ),
                                                modifier = Modifier.align(Alignment.CenterStart)
                                            )
                                            Image(
                                                painter = painterResource(R.drawable.ic_edit),
                                                contentDescription = null,
                                                modifier = Modifier.align(Alignment.CenterStart)
                                            )
                                        }
                                    }
                                    DropdownMenuItem(
                                        onClick = {
                                            // TODO 삭제
                                            isDropDownMenuExpanded = false
                                        },
                                        contentPadding = PaddingValues(0.dp),
                                        modifier = Modifier
                                            .width(100.dp)
                                            .height(40.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .padding(
                                                    vertical = Space8,
                                                    horizontal = Space12
                                                )
                                        ) {
                                            Text(
                                                text = "삭제",
                                                style = Body0.merge(
                                                    color = Gray700,
                                                    fontWeight = FontWeight.Normal
                                                ),
                                                modifier = Modifier.align(Alignment.CenterStart)
                                            )
                                            Image(
                                                painter = painterResource(R.drawable.ic_trash),
                                                contentDescription = null,
                                                modifier = Modifier.align(Alignment.CenterStart)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(Space16))
            Row(
                modifier = Modifier
                    .clickable {
                        // TODO open add group
                    }
                    .padding(
                        vertical = Space16,
                        horizontal = 2.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_circle_plus),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "그룹 등록하기",
                    style = Headline3.merge(
                        color = Gray600,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Spacer(modifier = Modifier.height(Space12))
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is GetGroupEvent.DeleteGroup -> {

                }
            }
        }
    }
}

@Preview(apiLevel = 33)
@Composable
private fun GetGroupScreenPreivew() {
    GetGroupScreen(
        appState = rememberApplicationState(),

        onDismissRequest = {},
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> },
        onResult = {

        },
        model = GetGroupModel(
            state = GetGroupState.Init,
            groups = listOf(
                Group(
                    id = 0,
                    name = "친구"
                ),
                Group(
                    id = 0,
                    name = "가족"
                ),
                Group(
                    id = 0,
                    name = "지인"
                ),
                Group(
                    id = 0,
                    name = "직장"
                ),
                Group(
                    id = 0,
                    name = "label"
                )
            )
        )
    )
}
