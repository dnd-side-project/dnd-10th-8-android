package ac.dnd.mour.android.presentation.ui.main.home.common.group.get

import ac.dnd.mour.android.domain.model.feature.group.Group
import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.theme.Body0
import ac.dnd.mour.android.presentation.common.theme.Gray000
import ac.dnd.mour.android.presentation.common.theme.Gray200
import ac.dnd.mour.android.presentation.common.theme.Gray500
import ac.dnd.mour.android.presentation.common.theme.Gray700
import ac.dnd.mour.android.presentation.common.theme.Gray800
import ac.dnd.mour.android.presentation.common.theme.Gray900
import ac.dnd.mour.android.presentation.common.theme.Headline2
import ac.dnd.mour.android.presentation.common.theme.Headline3
import ac.dnd.mour.android.presentation.common.theme.Shapes
import ac.dnd.mour.android.presentation.common.theme.Space12
import ac.dnd.mour.android.presentation.common.theme.Space16
import ac.dnd.mour.android.presentation.common.theme.Space20
import ac.dnd.mour.android.presentation.common.theme.Space8
import ac.dnd.mour.android.presentation.common.util.ErrorObserver
import ac.dnd.mour.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.mour.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.mour.android.presentation.common.util.logevent.LogEventUtil
import ac.dnd.mour.android.presentation.common.util.logevent.viewLogEvent
import ac.dnd.mour.android.presentation.common.view.BottomSheetScreen
import ac.dnd.mour.android.presentation.common.view.DialogScreen
import ac.dnd.mour.android.presentation.common.view.SnackBarScreen
import ac.dnd.mour.android.presentation.model.relation.DefaultGroupType
import ac.dnd.mour.android.presentation.ui.main.ApplicationState
import ac.dnd.mour.android.presentation.ui.main.home.common.group.add.AddGroupScreen
import ac.dnd.mour.android.presentation.ui.main.home.common.group.edit.EditGroupScreen
import ac.dnd.mour.android.presentation.ui.main.rememberApplicationState
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GetGroupScreen(
    appState: ApplicationState,
    groups: List<Group>,
    onDismissRequest: () -> Unit,
    onGroupChange: () -> Unit,
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

    LaunchedEffect(Unit) {
        viewLogEvent(
            LogEventUtil.VIEW_EDIT_GROUP,
            block = {

            }
        )
    }

    GetGroupScreen(
        appState = appState,
        onDismissRequest = onDismissRequest,
        onResult = onResult,
        onGroupChange = onGroupChange,
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
    onGroupChange: () -> Unit,
    model: GetGroupModel,
    intent: (GetGroupIntent) -> Unit,
    event: EventFlow<GetGroupEvent>,
    handler: CoroutineExceptionHandler
) {
    val scope = rememberCoroutineScope()
    var currentDeleteGroupIndex by remember { mutableIntStateOf(-1) }
    var currentEditGroupIndex by remember { mutableIntStateOf(-1) }
    var isShowingAddGroupSheet by remember { mutableStateOf(false) }
    var isShowingDeleteFailToast by remember { mutableStateOf(false) }

    fun deleteEvent(event: GetGroupEvent.DeleteGroup) {
        when (event) {
            is GetGroupEvent.DeleteGroup.Fail -> {
                scope.launch {
                    isShowingDeleteFailToast = true
                    delay(1000L)
                    isShowingDeleteFailToast = false
                }
            }

            is GetGroupEvent.DeleteGroup.Success -> {}
        }
    }

    BottomSheetScreen(
        onDismissRequest = onDismissRequest,
        properties = BottomSheetDialogProperties(
            behaviorProperties = BottomSheetBehaviorProperties(
                state = BottomSheetBehaviorProperties.State.Expanded,
                skipCollapsed = true
            )
        )
    ) {
        Box {
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
                        painter = painterResource(R.drawable.ic_close_rounded),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Gray900),
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(24.dp)
                            .clickable {
                                onDismissRequest()
                            }
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
                    items(model.groups.size) { index ->
                        val group = model.groups[index]
                        var isDropDownMenuExpanded by remember { mutableStateOf(false) }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(Shapes.large)
                                .background(color = Gray000)
                                .clickable {
                                    onDismissRequest()
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
                                    color = Gray800,
                                    fontWeight = FontWeight.SemiBold
                                ),
                                modifier = Modifier.weight(1f)
                            )

                            if (DefaultGroupType.checkEditable(group.name)) {
                                Box(modifier = Modifier.wrapContentSize()) {
                                    Image(
                                        painter = painterResource(R.drawable.ic_more_vertical),
                                        contentDescription = null,
                                        colorFilter = ColorFilter.tint(Gray500),
                                        modifier = Modifier
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
                                                    isDropDownMenuExpanded = false
                                                    currentEditGroupIndex = index
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
                                                        modifier = Modifier.align(Alignment.CenterEnd)
                                                    )
                                                }
                                            }
                                            DropdownMenuItem(
                                                onClick = {
                                                    isDropDownMenuExpanded = false
                                                    currentDeleteGroupIndex = index
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
                                                        modifier = Modifier.align(Alignment.CenterEnd)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(bottom = 84.dp))
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Gray200)
                    .padding(
                        horizontal = 20.dp,
                        vertical = 12.dp
                    )
                    .height(56.dp)
                    .align(Alignment.BottomCenter),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier
                        .clickable {
                            viewLogEvent(
                                LogEventUtil.CLICK_SAVE_GROUP_EDIT_GROUP,
                                block = {

                                }
                            )
                            isShowingAddGroupSheet = true
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
                            color = Gray700,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                Spacer(modifier = Modifier.height(Space12))
            }

            if (isShowingDeleteFailToast) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 65.dp)
                ) {
                    SnackBarScreen("등록된 관계가 존재하기 때문에 삭제할 수 없습니다.")
                }
            }
        }
    }

    if (currentDeleteGroupIndex != -1) {
        DialogScreen(
            isCancelable = true,
            message = "그룹을 삭제하시겠어요?",
            cancelMessage = "취소",
            confirmMessage = "삭제",
            onCancel = {
                currentDeleteGroupIndex = -1
            },
            onConfirm = {
                intent(GetGroupIntent.OnDelete(model.groups[currentDeleteGroupIndex].id))
                onGroupChange()
                currentDeleteGroupIndex = -1
            },
            onDismissRequest = {
                currentDeleteGroupIndex = -1
            }
        )
    }

    if (currentEditGroupIndex != -1) {
        EditGroupScreen(
            appState = appState,
            onDismissRequest = {
                currentEditGroupIndex = -1
            },
            prevGroup = model.groups[currentEditGroupIndex],
            onResult = {
                onGroupChange()
                currentEditGroupIndex = -1
            }
        )
    }

    if (isShowingAddGroupSheet) {
        AddGroupScreen(
            appState = appState,
            onDismissRequest = {
                isShowingAddGroupSheet = false
            },
            onResult = {
                onGroupChange()
                isShowingAddGroupSheet = false
            }
        )
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is GetGroupEvent.DeleteGroup -> deleteEvent(event)
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
        onResult = {},
        onGroupChange = {},
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
