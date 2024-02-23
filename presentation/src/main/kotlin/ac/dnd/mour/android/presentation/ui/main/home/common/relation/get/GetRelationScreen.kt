package ac.dnd.mour.android.presentation.ui.main.home.common.relation.get

import ac.dnd.mour.android.domain.model.feature.group.GroupWithRelationSimple
import ac.dnd.mour.android.domain.model.feature.relation.RelationSimple
import ac.dnd.mour.android.domain.model.feature.relation.RelationSimpleGroup
import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.theme.Body0
import ac.dnd.mour.android.presentation.common.theme.Body1
import ac.dnd.mour.android.presentation.common.theme.Gray000
import ac.dnd.mour.android.presentation.common.theme.Gray200
import ac.dnd.mour.android.presentation.common.theme.Gray300
import ac.dnd.mour.android.presentation.common.theme.Gray500
import ac.dnd.mour.android.presentation.common.theme.Gray600
import ac.dnd.mour.android.presentation.common.theme.Gray700
import ac.dnd.mour.android.presentation.common.theme.Gray800
import ac.dnd.mour.android.presentation.common.theme.Gray900
import ac.dnd.mour.android.presentation.common.theme.Headline2
import ac.dnd.mour.android.presentation.common.theme.Headline3
import ac.dnd.mour.android.presentation.common.theme.Primary3
import ac.dnd.mour.android.presentation.common.theme.Primary4
import ac.dnd.mour.android.presentation.common.theme.Shapes
import ac.dnd.mour.android.presentation.common.util.ErrorObserver
import ac.dnd.mour.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.mour.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.mour.android.presentation.common.view.BottomSheetScreen
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButton
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonProperties
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonSize
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonType
import ac.dnd.mour.android.presentation.common.view.textfield.TypingTextField
import ac.dnd.mour.android.presentation.common.view.textfield.TypingTextFieldType
import ac.dnd.mour.android.presentation.ui.main.ApplicationState
import ac.dnd.mour.android.presentation.ui.main.home.common.relation.RelationConstant
import ac.dnd.mour.android.presentation.ui.main.rememberApplicationState
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import kotlinx.coroutines.CoroutineExceptionHandler

@Composable
fun SearchRelationScreen(
    appState: ApplicationState,
    onDismissRequest: () -> Unit,
    onResult: (RelationSimple) -> Unit,
    viewModel: GetRelationViewModel = hiltViewModel()
) {
    val model: GetRelationModel = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()
        val groups by viewModel.groups.collectAsStateWithLifecycle()

        GetRelationModel(
            state = state,
            groups = groups
        )
    }
    ErrorObserver(viewModel)

    SearchRelationScreen(
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
private fun SearchRelationScreen(
    appState: ApplicationState,
    model: GetRelationModel,
    event: EventFlow<GetRelationEvent>,
    intent: (GetRelationIntent) -> Unit,
    handler: CoroutineExceptionHandler,
    onDismissRequest: () -> Unit,
    onResult: (RelationSimple) -> Unit,
) {
    var text by remember { mutableStateOf("") }
    var selectedRelation: RelationSimple? by remember { mutableStateOf(null) }
    var isTextFieldFocus by remember { mutableStateOf(false) }
    val lowerText = text.lowercase()
    val filteredGroups = model.groups.filter { group ->
        group.relationList.any { relation ->
            relation.name.lowercase().contains(lowerText)
        } || group.name.lowercase().contains(lowerText)
    }

    fun navigateToEditRelation() {
        onDismissRequest()
        appState.navController.navigate(RelationConstant.ROUTE)
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
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .background(Gray200)
                .padding(horizontal = 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
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
                Text(
                    text = "이름 선택",
                    style = Headline2.merge(
                        color = Gray900,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = "이전에 등록한 관계에서 찾아보세요.",
                    style = Body0.merge(
                        color = Gray800,
                        fontWeight = FontWeight.Normal
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                TypingTextField(
                    textType = TypingTextFieldType.Basic,
                    text = text,
                    onValueChange = {
                        text = it
                    },
                    fieldHeight = 39.dp,
                    isSingleLine = true,
                    textStyle = Body1.merge(
                        color = Gray700,
                        fontWeight = FontWeight.Normal
                    ),
                    contentPadding = PaddingValues(
                        start = if (!isTextFieldFocus && text.isEmpty()) 6.dp else 16.dp,
                    ),
                    backgroundColor = Gray000,
                    hintText = "이름을 입력하세요.",
                    hintTextColor = Gray500,
                    basicBorderColor = Color.Transparent,
                    leadingIconContent = {
                        if (!isTextFieldFocus && text.isEmpty()) {
                            Box(modifier = Modifier.padding(start = 16.dp)) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_search_group),
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    },
                    trailingIconContent = if (text.isNotEmpty()) {
                        {
                            Image(
                                painter = painterResource(R.drawable.ic_close_circle),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        text = ""
                                    }
                            )
                        }
                    } else null,
                    onTextFieldFocusChange = {
                        isTextFieldFocus = it
                    }
                )
                Spacer(modifier = Modifier.height(32.dp))
                if (filteredGroups.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(243.36.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "관계를 찾을 수 없어요.",
                            fontFamily = FontFamily(Font(R.font.pretendard)),
                            fontSize = 14.2.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = (-0.25).sp,
                            color = Gray700
                        )
                        Spacer(modifier = Modifier.height(16.22.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(Shapes.medium)
                                .clickable {
                                    navigateToEditRelation()
                                }
                                .background(color = Gray000)
                                .border(
                                    color = Gray500,
                                    width = 1.dp,
                                    shape = Shapes.medium
                                )
                                .padding(
                                    horizontal = 8.dp,
                                    vertical = 6.5.dp
                                )
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_plus),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                colorFilter = ColorFilter.tint(Gray700)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = "관계 등록하기",
                                style = Body1.merge(
                                    color = Gray700,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                } else {
                    LazyColumn {
                        filteredGroups.forEach { group ->
                            item {
                                var isExpanded by remember { mutableStateOf(false) }
                                SearchRelationGroup(
                                    group = group,
                                    selectedRelation = selectedRelation,
                                    isExpanded = isExpanded,
                                    onExpandRequest = {
                                        isExpanded = !isExpanded
                                    },
                                    onClick = { relation ->
                                        selectedRelation = relation
                                    }
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(Gray200)
                            .clickable {
                                navigateToEditRelation()
                            }
                            .padding(
                                start = 2.dp,
                                top = 16.dp,
                                bottom = 16.dp
                            )
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_circle_plus),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "관계 등록하기",
                            style = Headline3.merge(
                                color = Gray600,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Spacer(modifier = Modifier.height(52.dp))
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                ConfirmButton(
                    modifier = Modifier.fillMaxWidth(),
                    properties = ConfirmButtonProperties(
                        size = ConfirmButtonSize.Xlarge,
                        type = ConfirmButtonType.Primary
                    ),
                    isEnabled = selectedRelation != null,
                    onClick = {
                        selectedRelation?.let { relation ->
                            onResult(relation)
                            onDismissRequest()
                        }

                    }
                ) { style ->
                    Text(
                        text = "선택",
                        style = style
                    )
                }
            }
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->

        }
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun SearchRelationGroup(
    group: GroupWithRelationSimple,
    selectedRelation: RelationSimple?,
    isExpanded: Boolean,
    onExpandRequest: () -> Unit,
    onClick: (RelationSimple) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = Shapes.large,
            border =  BorderStroke(
                if (isExpanded) 2.dp else 1.dp,
                if (isExpanded) Primary4 else Gray300
            ),
            backgroundColor = if (isExpanded) Primary3 else Color.White,
            onClick = {
                onExpandRequest()
            },
            elevation = if (isExpanded) 10.dp else 0.dp
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = group.name,
                    style = Headline3.merge(
                        color = if(isExpanded) Gray000 else Gray900,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.width(2.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_person),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(if(isExpanded) Gray000 else Gray500)
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = group.relationList.size.toString(),
                    style = Body0.merge(
                        color = if(isExpanded) Gray000 else Gray800,
                        fontWeight = FontWeight.Normal
                    )
                )
                if (isExpanded) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_drop_down),
                        modifier = Modifier
                            .rotate(180f)
                            .size(24.dp),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Gray000)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.ic_drop_down),
                        modifier = Modifier.size(24.dp),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Gray600)
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
            }
        }

        if (isExpanded) {
            Spacer(modifier = Modifier.height(6.dp))
            Card(
                border = BorderStroke(2.dp, Gray500),
                backgroundColor = Gray000,
                shape = Shapes.large,
                elevation = 10.dp
            ) {
                Column {
                    group.relationList.forEachIndexed { index, relation ->
                        SearchRelationRelation(relation, selectedRelation, onClick)
                        if (index < group.relationList.size - 1) {
                            Divider(modifier = Modifier.height(1.dp))
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun SearchRelationRelation(
    relation: RelationSimple,
    selectedRelation: RelationSimple?,
    onClick: (RelationSimple) -> Unit
) {
    Surface(
        color = Gray000,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        onClick = {
            onClick(relation)
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = relation.name,
                style = Headline3.merge(
                    color = Gray800,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = "・",
                style = Headline3
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                modifier = Modifier.weight(1f),
                text = relation.group.name,
                style = Body0.merge(
                    color = Gray700,
                    fontWeight = FontWeight.Normal
                )
            )
            Image(
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onClick(relation)
                    },
                painter = if (relation == selectedRelation) {
                    painterResource(R.drawable.ic_check_circle)
                } else {
                    painterResource(R.drawable.ic_check_circle_gray)
                },
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(20.dp))
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun SearchRelationScreenPreview() {
    SearchRelationScreen(
        appState = rememberApplicationState(),
        model = GetRelationModel(state = GetRelationState.Init, groups = listOf(
            GroupWithRelationSimple(
                id = 0,
                name = "sdfdsfs",
                relationList = listOf(
                   RelationSimple(
                       id = 0,
                       name = "sdljsfldfsj",
                       group = RelationSimpleGroup(
                           id = 0,
                           name = "fewljfwej"
                       )
                   )
                )
            )
        )),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> },
        onDismissRequest = {},
        onResult = {}
    )
}
