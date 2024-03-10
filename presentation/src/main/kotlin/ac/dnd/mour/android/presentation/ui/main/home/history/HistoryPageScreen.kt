package ac.dnd.mour.android.presentation.ui.main.home.history

import ac.dnd.mour.android.domain.model.feature.group.GroupWithRelationDetail
import ac.dnd.mour.android.domain.model.feature.relation.RelationDetail
import ac.dnd.mour.android.domain.model.feature.relation.RelationDetailGroup
import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.theme.Body1
import ac.dnd.mour.android.presentation.common.theme.Gray000
import ac.dnd.mour.android.presentation.common.theme.Gray200
import ac.dnd.mour.android.presentation.common.theme.Gray400
import ac.dnd.mour.android.presentation.common.theme.Gray600
import ac.dnd.mour.android.presentation.common.theme.Gray700
import ac.dnd.mour.android.presentation.common.theme.Gray800
import ac.dnd.mour.android.presentation.common.theme.Shapes
import ac.dnd.mour.android.presentation.common.theme.Space16
import ac.dnd.mour.android.presentation.common.theme.Space24
import ac.dnd.mour.android.presentation.common.theme.Space4
import ac.dnd.mour.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.mour.android.presentation.common.util.logevent.LogEventUtil
import ac.dnd.mour.android.presentation.common.util.logevent.viewLogEvent
import ac.dnd.mour.android.presentation.common.util.makeRoute
import ac.dnd.mour.android.presentation.common.view.chip.ChipItem
import ac.dnd.mour.android.presentation.common.view.chip.ChipType
import ac.dnd.mour.android.presentation.model.history.HistorySortedType
import ac.dnd.mour.android.presentation.model.history.HistoryViewType
import ac.dnd.mour.android.presentation.model.relation.DefaultGroupType
import ac.dnd.mour.android.presentation.ui.main.ApplicationState
import ac.dnd.mour.android.presentation.ui.main.home.history.detail.HistoryDetailConstant
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineExceptionHandler

@SuppressLint("InvalidColorHexValue")
@Composable
fun HistoryPageScreen(
    appState: ApplicationState,
    model: HistoryModel,
    event: EventFlow<HistoryEvent>,
    intent: (HistoryIntent) -> Unit,
    handler: CoroutineExceptionHandler,
    viewType: HistoryViewType,
    searchText: String,
    onRecord: () -> Unit,
    isExpanded: Boolean
) {
    var selectedGroupId by remember { mutableLongStateOf(-1) }
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }
    var viewSortType by remember { mutableStateOf(HistorySortedType.LATEST) }
    val groups = model.groups.map { group ->
        GroupWithRelationDetail(
            id = group.id,
            name = group.name,
            relationList = group.relationList.filter { relation ->
                return@filter when (viewType) {
                    HistoryViewType.TOTAL -> true
                    HistoryViewType.GIVE -> relation.giveMoney > 0
                    HistoryViewType.TAKE -> relation.takeMoney > 0
                }
            }
        )
    }.sortedByDescending {
        it.relationList.size
    }

    val relations = groups.flatMap { it.relationList }
        .sortedByDescending {
            when (viewSortType) {
                HistorySortedType.LATEST -> it.id
                HistorySortedType.INTIMACY -> when (viewType) {
                    HistoryViewType.TOTAL -> it.giveMoney + it.takeMoney
                    HistoryViewType.GIVE -> it.giveMoney
                    HistoryViewType.TAKE -> it.takeMoney
                }
            }
        }
        .filter {
            if (selectedGroupId == -1L) true
            else if (selectedGroupId < 0) false
            else it.group.id == selectedGroupId
        }
        .filter {
            if (searchText.isNotEmpty()) {
                it.name.contains(searchText)
            } else {
                true
            }
        }

    fun navigateToHistoryDetail(id: Long) {
        val route = makeRoute(
            HistoryDetailConstant.ROUTE,
            listOf(HistoryDetailConstant.ROUTE_ARGUMENT_ID to id)
        )
        appState.navController.navigate(route)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Gray200)
        ) {
            Spacer(modifier = Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(
                        bottom = 2.dp,
                        start = 20.dp,
                        end = 20.dp
                    ),
                contentAlignment = Alignment.CenterStart
            ) {
                GroupChipListComponent(
                    currentSelectedId = selectedGroupId,
                    groups = groups,
                    relationCount = model.groups.flatMap { it.relationList }.size,
                    onSelectChip = { groupId ->
                        selectedGroupId = groupId
                    }
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .width(56.dp)
                        .fillMaxHeight()
                        .background(
                            brush = Brush.horizontalGradient(
                                listOf(
                                    Color(0x00F6F6F7),
                                    Color(0xFFF6F6F7),
                                )
                            ),
                            alpha = 0.4f
                        )
                )
            }
            Box(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "내역",
                    style = Body1.merge(
                        color = Gray700,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable {
                            isDropDownMenuExpanded = true
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = viewSortType.typeName,
                        style = Body1.merge(
                            color = Gray800,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Image(
                        painter = painterResource(R.drawable.ic_chevron_down),
                        contentDescription = null,
                        modifier = Modifier.size(Space16)
                    )
                }
            }

            if (relations.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    EmptyRelationView(
                        onRecord = onRecord,
                        isExpanded = isExpanded
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(Space16),
                    verticalArrangement = Arrangement.spacedBy(Space16),
                    contentPadding = PaddingValues(
                        horizontal = 20.dp,
                        vertical = 12.dp
                    )
                ) {
                    items(relations) { relation ->
                        HistoryRelationItem(
                            viewType = viewType,
                            relation = relation,
                            onSelectCard = {
                                navigateToHistoryDetail(it.id)
                            },
                        )
                    }
                    item(1) {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
        }

        if (isDropDownMenuExpanded) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .clickable {
                        isDropDownMenuExpanded = false
                    }
            ) {
                Card(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(
                            top = 86.dp,
                            end = 20.dp
                        )
                        .background(
                            color = Gray000,
                            shape = Shapes.medium
                        ),
                    elevation = 5.dp
                ) {
                    Column(verticalArrangement = Arrangement.Center) {
                        HistorySortedType.entries.forEachIndexed { index, type ->
                            Row(
                                modifier = Modifier
                                    .clickable {
                                        viewSortType = type
                                        isDropDownMenuExpanded = false
                                    }
                                    .padding(start = 6.dp, end = 34.dp)
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (viewSortType == type) {
                                    Image(
                                        painter = painterResource(R.drawable.ic_history_check),
                                        contentDescription = null,
                                    )
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .size(Space24)
                                            .background(Color.Transparent)
                                    )
                                }
                                Spacer(Modifier.width(Space4))
                                Text(
                                    text = type.typeName,
                                    style = Body1.merge(
                                        color = Gray800,
                                        fontWeight = FontWeight.Normal
                                    )
                                )
                            }
                            if (index != HistorySortedType.entries.lastIndex) {
                                Box(
                                    modifier = Modifier
                                        .height(1.dp)
                                        .padding(top = 0.5.dp)
                                        .background(color = Gray200)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GroupChipListComponent(
    currentSelectedId: Long,
    onSelectChip: (Long) -> Unit,
    relationCount: Int,
    groups: List<GroupWithRelationDetail>
) {
    val defaultList = DefaultGroupType.entries
        .map { it.getTypeName() }
        .filter { value ->
            value !in groups.map { it.name }
        }

    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        item(1) {
            ChipItem(
                chipType = ChipType.MAIN,
                currentSelectedId = setOf(currentSelectedId),
                chipId = -1L,
                chipText = "전체",
                chipCount = relationCount,
                onSelectChip = {
                    onSelectChip(-1L)
                }
            )
        }
        items(groups) { group ->
            ChipItem(
                chipType = ChipType.MAIN,
                currentSelectedId = setOf(currentSelectedId),
                chipId = group.id,
                chipText = group.name,
                chipCount = group.relationList.size,
                onSelectChip = {
                    onSelectChip(group.id)
                }
            )
        }

        items(defaultList.size) { index ->
            ChipItem(
                chipType = ChipType.MAIN,
                currentSelectedId = setOf(currentSelectedId),
                chipId = (-index - 2).toLong(),
                chipText = defaultList[index],
                chipCount = 0,
                onSelectChip = {
                    onSelectChip((-index - 2).toLong())
                }
            )
        }
    }
}

@Composable
private fun EmptyRelationView(
    isExpanded: Boolean,
    onRecord: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(if (isExpanded) 1f else 60f))
        Text(
            text = "아직 주고 받은 내역이 없어요.",
            style = Body1.merge(
                color = Gray700,
                fontWeight = FontWeight.SemiBold
            ),
            letterSpacing = (-0.25).sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "관계를 등록하고 마음을 기록해 보세요",
            style = Body1.merge(
                color = Gray600,
                fontWeight = FontWeight.Medium
            ),
            letterSpacing = (-0.25).sp
        )
        Spacer(modifier = Modifier.height(24.dp))
        Box(
            modifier = Modifier
                .clip(Shapes.medium)
                .background(color = Gray000)
                .clickable {
                    onRecord()
                    viewLogEvent(
                        LogEventUtil.VIEW_MAIN,
                        LogEventUtil.CLICK_PLUSBTN_MAIN
                    )
                }
                .border(
                    width = 1.dp,
                    color = Gray400,
                    shape = Shapes.medium
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 6.5.dp
                )
        ) {
            Text(
                text = "관계 등록하기",
                style = Body1.merge(
                    color = Gray600,
                    fontWeight = FontWeight.SemiBold
                ),
                letterSpacing = (-0.25).sp
            )
        }
        Spacer(modifier = Modifier.weight(if (isExpanded) 1f else 118f))
    }
}

@Composable
@Preview
private fun EmptyRelationViewPreview() {
    Box(modifier = Modifier.height(198.dp)) {
        EmptyRelationView(
            onRecord = {},
            isExpanded = true
        )
    }
}

@Composable
@Preview
private fun GroupChipListComponent1Preview() {
    GroupChipListComponent(
        currentSelectedId = -1,
        groups = listOf(),
        relationCount = 10,
        onSelectChip = {

        }
    )
}

@Composable
@Preview
private fun GroupChipListComponent2Preview() {
    GroupChipListComponent(
        currentSelectedId = -1,
        groups = listOf(
            GroupWithRelationDetail(
                id = 0,
                name = "전체",
                relationList = listOf(
                    RelationDetail(
                        id = 0,
                        name = "이름",
                        group = RelationDetailGroup(
                            id = 0,
                            name = "친구"
                        ),
                        giveMoney = 10000,
                        takeMoney = 10000
                    )
                )
            ),
            GroupWithRelationDetail(
                id = 1,
                name = "친구",
                relationList = listOf(
                    RelationDetail(
                        id = 0,
                        name = "이름",
                        group = RelationDetailGroup(
                            id = 0,
                            name = "친구"
                        ),
                        giveMoney = 10000,
                        takeMoney = 10000
                    )
                )
            ),
            GroupWithRelationDetail(
                id = 1,
                name = "가족",
                relationList = listOf(
                    RelationDetail(
                        id = 0,
                        name = "이름",
                        group = RelationDetailGroup(
                            id = 0,
                            name = "친구"
                        ),
                        giveMoney = 10000,
                        takeMoney = 10000
                    )
                )
            ),
            GroupWithRelationDetail(
                id = 1,
                name = "지인",
                relationList = listOf(
                    RelationDetail(
                        id = 0,
                        name = "이름",
                        group = RelationDetailGroup(
                            id = 0,
                            name = "친구"
                        ),
                        giveMoney = 10000,
                        takeMoney = 10000
                    )
                )
            ),
            GroupWithRelationDetail(
                id = 1,
                name = "직장",
                relationList = listOf(
                    RelationDetail(
                        id = 0,
                        name = "이름",
                        group = RelationDetailGroup(
                            id = 0,
                            name = "친구"
                        ),
                        giveMoney = 10000,
                        takeMoney = 10000
                    )
                )
            ),
            GroupWithRelationDetail(
                id = 1,
                name = "지인2",
                relationList = listOf(
                    RelationDetail(
                        id = 0,
                        name = "이름",
                        group = RelationDetailGroup(
                            id = 0,
                            name = "친구"
                        ),
                        giveMoney = 10000,
                        takeMoney = 10000
                    )
                )
            )
        ),
        relationCount = 10,
        onSelectChip = {

        }
    )
}
