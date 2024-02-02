package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.main.detail

import ac.dnd.bookkeeping.android.domain.model.history.HistoryInfo
import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Body1
import ac.dnd.bookkeeping.android.presentation.common.theme.Body2
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray000
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray100
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray200
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray300
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray600
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray700
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray800
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline2
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary4
import ac.dnd.bookkeeping.android.presentation.common.theme.Shapes
import ac.dnd.bookkeeping.android.presentation.common.theme.Space12
import ac.dnd.bookkeeping.android.presentation.common.theme.Space16
import ac.dnd.bookkeeping.android.presentation.common.theme.Space20
import ac.dnd.bookkeeping.android.presentation.common.theme.Space24
import ac.dnd.bookkeeping.android.presentation.common.theme.Space4
import ac.dnd.bookkeeping.android.presentation.common.theme.Space8
import ac.dnd.bookkeeping.android.presentation.common.view.chip.ChipType
import ac.dnd.bookkeeping.android.presentation.common.view.chip.GroupChipListComponent
import ac.dnd.bookkeeping.android.presentation.common.view.textfield.TypingTextField
import ac.dnd.bookkeeping.android.presentation.common.view.textfield.TypingTextFieldType
import ac.dnd.bookkeeping.android.presentation.ui.main.home.history.main.HistoryMainModel
import ac.dnd.bookkeeping.android.presentation.ui.main.home.history.main.HistoryMainState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.history.main.detail.item.HistoryRelationItem
import ac.dnd.bookkeeping.android.presentation.ui.main.home.history.main.detail.type.HistorySortedType
import ac.dnd.bookkeeping.android.presentation.ui.main.home.history.main.detail.type.HistoryViewType
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@SuppressLint("InvalidColorHexValue")
@Composable
fun HistoryDetailScreen(
    viewType: HistoryViewType,
    mainModel: HistoryMainModel,
    viewModel: HistoryDetailViewModel = hiltViewModel()
) {
    val model: HistoryDetailModel = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()
        val groups by viewModel.groups.collectAsStateWithLifecycle()

        HistoryDetailModel(
            state = state,
            viewType = viewType,
            historyGroups = groups,
        )
    }

    LaunchedEffect(Unit) {
        viewModel.loadHistoryData(viewType)
    }

    var searchText by remember { mutableStateOf("") }
    var selectedGroupId by remember {
        mutableLongStateOf(
            model.historyGroups.firstOrNull()?.id ?: 0
        )
    }
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }
    var viewSortType by remember { mutableStateOf(HistorySortedType.LATEST) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Gray000,
                    shape = RoundedCornerShape(
                        bottomStart = Space12,
                        bottomEnd = Space12
                    )
                )
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 28.dp,
                    bottom = 18.dp
                )
        ) {
            Text(
                text = buildAnnotatedString {
                    append("총 ")
                    withStyle(
                        SpanStyle(color = Primary4)
                    ) {
                        //TODO load
                        append(mainModel.historyInfo.totalHeartCount.toString())
                    }
                    append("의 마음을\n주고 받았어요 ")
                },
                style = Headline2.merge(
                    color = Gray800,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            TypingTextField(
                textType = TypingTextFieldType.Basic,
                text = searchText,
                onValueChange = {
                    searchText = it
                },
                contentPadding = PaddingValues(
                    top = 9.dp,
                    bottom = 9.dp,
                    start = if (searchText.isEmpty()) 0.dp else 12.dp,
                    end = 20.dp,
                ),
                basicBorderColor = Gray100,
                backgroundColor = Gray100,
                hintText = "이름을 입력하세요.",
                leadingIconContent = if (searchText.isEmpty()) {
                    {
                        Image(
                            painter = painterResource(R.drawable.ic_search),
                            contentDescription = null,
                            modifier = Modifier.size(Space16)
                        )
                    }
                } else null,
                trailingIconContent = if (searchText.isNotEmpty()) {
                    {
                        Image(
                            painter = painterResource(R.drawable.ic_close_circle),
                            contentDescription = null,
                            modifier = Modifier
                                .size(Space20)
                                .clickable {
                                    searchText = ""
                                }
                        )
                    }
                } else null
            )
            if (mainModel.historyInfo.unWrittenCount > 0) {
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Gray300,
                            shape = Shapes.medium,
                        )
                        .fillMaxWidth()
                        .padding(
                            horizontal = Space20,
                            vertical = Space16
                        )

                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_close_circle),
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.TopEnd)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        //TODO into icon
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(color = Gray200)
                                .size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(Space8))
                        Column {
                            Text(
                                text = buildAnnotatedString {
                                    append("지난 일정 ")
                                    withStyle(
                                        SpanStyle(color = Gray800)
                                    ) {
                                        append("${mainModel.historyInfo.unWrittenCount}개")
                                    }
                                },
                                style = Body1.merge(
                                    color = Gray700,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "한번에 기록하기",
                                    style = Body2.merge(
                                        color = Gray600,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                                Image(
                                    painter = painterResource(R.drawable.ic_chevron_right),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(16.dp)
                                        .height(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        Box(
            modifier = Modifier.padding(
                vertical = 16.dp,
                horizontal = 20.dp
            )
        ) {
            GroupChipListComponent(
                chipType = ChipType.MAIN,
                currentSelectedId = selectedGroupId,
                groups = model.historyGroups,
                onSelectChip = { group ->
                    selectedGroupId = group.id
                }
            )
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        horizontal = 6.dp,
                        vertical = 3.5.dp
                    )
                    .clickable {
                        isDropDownMenuExpanded = true
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = viewSortType.typeName,
                    style = Body1.merge(
                        color = Gray700,
                        fontWeight = FontWeight.Normal
                    )
                )
                Spacer(modifier = Modifier.width(2.dp))
                Image(
                    painter = painterResource(R.drawable.ic_chevron_down),
                    contentDescription = null,
                    modifier = Modifier.size(Space16)
                )
                DropdownMenu(
                    modifier = Modifier
                        .wrapContentHeight()
                        .background(
                            color = Gray000,
                            shape = Shapes.medium
                        ),
                    expanded = isDropDownMenuExpanded,
                    onDismissRequest = { isDropDownMenuExpanded = false }
                ) {
                    Column(verticalArrangement = Arrangement.Center) {
                        HistorySortedType.entries.forEachIndexed { index, type ->
                            DropdownMenuItem(
                                onClick = {},
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier
                                    .width(116.dp)
                                    .height(40.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .clickable {
                                            viewSortType = type
                                            isDropDownMenuExpanded = false
                                        }
                                        .padding(horizontal = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (viewSortType == type) {
                                        Image(
                                            painter = painterResource(R.drawable.ic_check_line),
                                            contentDescription = null,
                                            colorFilter = ColorFilter.tint(Primary4),
                                            modifier = Modifier.size(Space24)
                                        )
                                    } else {
                                        Box(
                                            modifier = Modifier.size(Space24)
                                                .background(Color.White)
                                        )
                                    }
                                    Spacer(Modifier.width(Space4))
                                    Text(
                                        text = type.typeName,
                                        style = Body1.merge(
                                            color = Gray700,
                                            fontWeight = FontWeight.Normal
                                        )
                                    )
                                }
                            }
                            if (index != HistorySortedType.entries.lastIndex) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
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

        model.historyGroups.find { it.id == selectedGroupId }?.let {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(Space16),
                verticalArrangement = Arrangement.spacedBy(Space16),
                contentPadding = PaddingValues(
                    horizontal = 20.dp,
                    vertical = 16.dp
                )
            ) {
                items(it.relations) { relation ->
                    HistoryRelationItem(
                        relation,
                        onSelectCard = {

                        }
                    )
                }
            }
        } ?: EmptyRelationView()
    }
}

@Composable
fun EmptyRelationView() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.weight(107.66f))
        Text(
            text = "아직 경조사비 내역이 입력되지 않았어요.",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = Body1.merge(
                color = Gray600,
                fontWeight = FontWeight.SemiBold
            )
        )
        Spacer(modifier = Modifier.weight(188.34f))
    }
}


@Preview(showBackground = true)
@Composable
fun HistoryDetailPreview() {
    HistoryDetailScreen(
        HistoryViewType.TOTAL,
        mainModel = HistoryMainModel(
            state = HistoryMainState.Init,
            historyInfo = HistoryInfo(
                unReadAlarm = true,
                totalHeartCount = 30,
                unWrittenCount = 5
            )
        ),
    )
}
