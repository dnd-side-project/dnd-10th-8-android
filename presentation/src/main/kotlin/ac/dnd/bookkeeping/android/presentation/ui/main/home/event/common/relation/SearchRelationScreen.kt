package ac.dnd.bookkeeping.android.presentation.ui.main.home.event.common.relation

import ac.dnd.bookkeeping.android.domain.model.feature.group.GroupWithRelation
import ac.dnd.bookkeeping.android.domain.model.feature.relation.RelationSimple
import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Body0
import ac.dnd.bookkeeping.android.presentation.common.theme.Body1
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray000
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray200
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray500
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray700
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline2
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline3
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary4
import ac.dnd.bookkeeping.android.presentation.common.theme.Shapes
import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.common.view.BottomSheetScreen
import ac.dnd.bookkeeping.android.presentation.common.view.CustomTextField
import ac.dnd.bookkeeping.android.presentation.common.view.DialogScreen
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButton
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonProperties
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonSize
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonType
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.rememberApplicationState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    viewModel: SearchRelationViewModel = hiltViewModel()
) {
    val model: SearchRelationModel = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()
        val groups by viewModel.groups.collectAsStateWithLifecycle()

        SearchRelationModel(
            state = state,
            groups = groups
        )
    }
    ErrorObserver(viewModel)

    var isDialogShowing by remember { mutableStateOf(false) }

    DialogScreen(
        isShowing = isDialogShowing,
        title = stringResource(id = R.string.error_dialog_title),
        onDismissRequest = {
            isDialogShowing = false
        }
    )

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
@OptIn(ExperimentalMaterialApi::class)
private fun SearchRelationScreen(
    appState: ApplicationState,
    model: SearchRelationModel,
    event: EventFlow<SearchRelationEvent>,
    intent: (SearchRelationIntent) -> Unit,
    handler: CoroutineExceptionHandler,
    onDismissRequest: () -> Unit,
    onResult: (RelationSimple) -> Unit,
) {
    var text by remember { mutableStateOf("") }
    var selectedRelation: RelationSimple? by remember { mutableStateOf(null) }

    val lowerText = text.lowercase()
    val filteredGroups = model.groups.filter { group ->
        group.relationList.any { relation ->
            relation.name.lowercase().contains(lowerText)
        } || group.name.lowercase().contains(lowerText)
    }

    fun navigateToEditRelation() {
        // TODO : EmptyCase
//        appState.navController.navigate(EditRelationConstant.ROUTE)
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
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .background(Gray200)
                .padding(20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "이름 선택",
                style = Headline2
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "이전에 등록한 관계에서 찾아보세요.",
                style = Headline2
            )
            Spacer(modifier = Modifier.height(20.dp))
            CustomTextField(
                text = text,
                height = 34.dp,
                isSingleLine = true,
                textStyle = Body1,
                backgroundColor = Gray200,
                elevation = 0.dp,
                onTextChange = {
                    text = it
                },
                hintTextContent = {
                    Text(
                        text = "검색어를 입력하세요.",
                        style = Body1.merge(color = Gray700)
                    )
                },
                leadingIconContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
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

            Spacer(modifier = Modifier.height(10.dp))

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
            Spacer(modifier = Modifier.height(10.dp))
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
    group: GroupWithRelation,
    selectedRelation: RelationSimple?,
    isExpanded: Boolean,
    onExpandRequest: () -> Unit,
    onClick: (RelationSimple) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = Shapes.large,
            border = BorderStroke(1.dp, Gray500),
            color = Color.White,
            onClick = {
                onExpandRequest()
            }
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = group.name,
                    style = Headline3
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_person),
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = group.relationList.size.toString(),
                    style = Headline3.merge(Gray700)
                )
                if (isExpanded) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chevron_up),
                        contentDescription = null
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chevron_down),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
            }
        }

        if (isExpanded) {
            Spacer(modifier = Modifier.height(6.dp))
            Surface(
                border = BorderStroke(1.dp, Gray500),
                color = Gray000,
                shape = Shapes.large
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
                style = Headline3
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
                style = Body0
            )
            RadioButton(
                modifier = Modifier.size(24.dp),
                selected = relation == selectedRelation,
                onClick = { onClick(relation) },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Primary4,
                    unselectedColor = Gray500
                )
            )
            Spacer(modifier = Modifier.width(20.dp))
        }
    }
}

// TODO : Preview 안보임.
@Preview
@Composable
fun SearchRelationScreenPreview() {
    SearchRelationScreen(
        appState = rememberApplicationState(),
        model = SearchRelationModel(state = SearchRelationState.Init, groups = listOf()),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> },
        onDismissRequest = {},
        onResult = {}
    )
}
