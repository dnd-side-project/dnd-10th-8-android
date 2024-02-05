package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.relation.add

import ac.dnd.bookkeeping.android.domain.model.feature.group.Group
import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Caption1
import ac.dnd.bookkeeping.android.presentation.common.theme.Caption2
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray000
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray200
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray400
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray500
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray600
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray700
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray800
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline1
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline3
import ac.dnd.bookkeeping.android.presentation.common.theme.Shapes
import ac.dnd.bookkeeping.android.presentation.common.theme.Space12
import ac.dnd.bookkeeping.android.presentation.common.theme.Space20
import ac.dnd.bookkeeping.android.presentation.common.theme.Space24
import ac.dnd.bookkeeping.android.presentation.common.theme.Space4
import ac.dnd.bookkeeping.android.presentation.common.theme.Space56
import ac.dnd.bookkeeping.android.presentation.common.theme.Space8
import ac.dnd.bookkeeping.android.presentation.common.theme.Space80
import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.common.util.expansion.addFocusCleaner
import ac.dnd.bookkeeping.android.presentation.common.view.chip.ChipItem
import ac.dnd.bookkeeping.android.presentation.common.view.chip.ChipType
import ac.dnd.bookkeeping.android.presentation.common.view.component.FieldSelectComponent
import ac.dnd.bookkeeping.android.presentation.common.view.component.FieldSubject
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButton
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonProperties
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonSize
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonType
import ac.dnd.bookkeeping.android.presentation.common.view.textfield.TypingTextField
import ac.dnd.bookkeeping.android.presentation.common.view.textfield.TypingTextFieldType
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.rememberApplicationState
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AddRelationScreen(
    appState: ApplicationState,
    viewModel: AddRelationViewModel = hiltViewModel()
) {
    val model :AddRelationModel = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()
        val groups by viewModel.groups.collectAsStateWithLifecycle()

        AddRelationModel(
            state = state,
            groups = groups
        )
    }
    ErrorObserver(viewModel)

    val focusManager = LocalFocusManager.current
    val isRecordState by remember { mutableStateOf(false) }
    val isSaveState by remember { mutableStateOf(false) }
    var isNameTypeChipType by remember { mutableStateOf(true) }
    var currentNameText by remember { mutableStateOf("닉네임 입력 (15자 이내)") }
    var kakaoNameText by remember { mutableStateOf("카카오에서 친구 선택") }
    var isShowingKakaoPicker by remember { mutableStateOf(false) }
    var currentGroupId by remember { mutableLongStateOf(-1) }
    var memoText by remember { mutableStateOf("") }

    val recordButtonTextColorState = animateColorAsState(
        targetValue = if (isRecordState) Gray700 else Gray000,
        label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray000)
            .addFocusCleaner(focusManager)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .height(Space56)
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp,
                    vertical = 13.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_chevron_left),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(Space4))
            Text(
                text = "관계 등록하기",
                style = Headline1.merge(
                    color = Gray800,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Space20)
                .padding(top = 96.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Space80),
                contentAlignment = Alignment.Center
            ) {
                Box {
                    // TODO image
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clip(CircleShape)
                            .background(Gray400)
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_camera),
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )
                }
            }
            Spacer(modifier = Modifier.height(60.dp))
            FieldSubject(subject = "이름")
            Spacer(modifier = Modifier.height(Space12))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                ChipItem(
                    chipType = if (isNameTypeChipType) ChipType.MAIN else ChipType.BORDER,
                    chipText = "직접 입력",
                    currentSelectedId = setOf(if (isNameTypeChipType) 0 else 1),
                    chipId = 0,
                    onSelectChip = {
                        isNameTypeChipType = true
                    }
                )
                ChipItem(
                    chipType = if (isNameTypeChipType) ChipType.BORDER else ChipType.MAIN,
                    chipText = "카카오톡으로 등록",
                    chipId = 1,
                    currentSelectedId = setOf(if (isNameTypeChipType) 0 else 1),
                    onSelectChip = {
                        isNameTypeChipType = false
                    }
                )
            }
            Spacer(modifier = Modifier.height(Space12))
            if (isNameTypeChipType) {
                TypingTextField(
                    textType = TypingTextFieldType.Basic,
                    text = currentNameText,
                    onValueChange = {
                        currentNameText = it
                    }
                )
            } else {
                FieldSelectComponent(
                    text = kakaoNameText,
                    isSelected = isShowingKakaoPicker,
                    onClick = {
                        isShowingKakaoPicker = true
                    }
                )
            }
            Spacer(modifier = Modifier.height(Space24))
            FieldSubject(subject = "그룹")
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(model.groups){ group ->
                    ChipItem(
                        chipType = ChipType.BORDER,
                        currentSelectedId = setOf(currentGroupId),
                        chipText = group.name,
                        onSelectChip = {
                            currentGroupId = it
                        },
                        chipId = group.id
                    )
                }
            }
            Spacer(modifier = Modifier.height(Space12))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(Shapes.medium)
                    .background(color = Gray200)
                    .clickable {
                        // TODO open edit
                    }
                    .padding(
                        horizontal = Space8,
                        vertical = 5.dp
                    )
            ){
                Image(
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "편집",
                    style = Caption2.merge(
                        color = Gray600,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
            Spacer(modifier = Modifier.height(Space24))
            FieldSubject(
                subject  = "메모",
                isViewIcon = false
            )
            Spacer(
                modifier = Modifier.height(6.dp)
            )
            TypingTextField(
                text = memoText,
                onValueChange = {
                    memoText = it
                },
                textType = TypingTextFieldType.LongSentence
            )
        }

        Row(
            modifier = Modifier
                .background(color = Gray000)
                .align(Alignment.BottomCenter)
                .padding(
                    vertical = Space12,
                    horizontal = Space20
                ),
            horizontalArrangement = Arrangement.spacedBy(Space12)
        ) {
            ConfirmButton(
                modifier = Modifier.weight(1f),
                properties = ConfirmButtonProperties(
                    size = ConfirmButtonSize.Xlarge,
                    type = ConfirmButtonType.Secondary
                ),
                content = {
                    Text(
                        text = "바로 마음 기록",
                        style = Headline3.merge(
                            color = recordButtonTextColorState.value,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                },
                isEnabled = isRecordState
            )
            ConfirmButton(
                modifier = Modifier.weight(1f),
                properties = ConfirmButtonProperties(
                    size = ConfirmButtonSize.Xlarge,
                    type = ConfirmButtonType.Primary
                ),
                content = {
                    Text(
                        text = "저장하기",
                        style = Headline3.merge(
                            color = Gray000,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                },
                isEnabled = isSaveState
            )
        }

    }

    LaunchedEffectWithLifecycle(viewModel.event, viewModel.handler) {
        viewModel.event.eventObserve { event ->

        }
    }
}

@Preview
@Composable
fun AddRelationScreenPreview() {
    AddRelationScreen(
        appState = rememberApplicationState()
    )
}
