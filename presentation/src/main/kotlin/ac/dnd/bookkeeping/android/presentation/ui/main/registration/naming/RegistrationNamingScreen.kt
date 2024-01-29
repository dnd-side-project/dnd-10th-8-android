package ac.dnd.bookkeeping.android.presentation.ui.main.registration.naming

import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Body1
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray200
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray600
import ac.dnd.bookkeeping.android.presentation.common.theme.Space12
import ac.dnd.bookkeeping.android.presentation.common.theme.Space20
import ac.dnd.bookkeeping.android.presentation.common.theme.Space48
import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.common.view.CustomTextField
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButton
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonProperties
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonSize
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonType
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.registration.collecting.RegistrationCollectingConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.rememberApplicationState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineExceptionHandler

@Composable
fun RegistrationNamingScreen(
    appState: ApplicationState,
    model: RegistrationNamingModel,
    event: EventFlow<RegistrationNamingEvent>,
    intent: (RegistrationNamingIntent) -> Unit,
    handler: CoroutineExceptionHandler
) {

    var userNameText by remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .height(80.dp)
                .padding(
                    start = 10.dp,
                    bottom = 12.67.dp
                ),
            contentAlignment = Alignment.BottomStart
        ) {
            Image(
                painter = painterResource(R.drawable.ic_chevron_left),
                modifier = Modifier
                    .size(24.dp)
                    .clickable {

                    },
                contentDescription = "back press"
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(
                    start = Space20,
                    end = Space20,
                    bottom = 220.dp
                )
        ) {
            Text(
                text = "닉네임",
                style = Body1.merge(color = Gray600)
            )
            Spacer(Modifier.height(Space12))
            Row {
                CustomTextField(
                    text = userNameText.text,
                    modifier = Modifier.weight(1f),
                    height = Space48,
                    contentInnerPadding = PaddingValues(horizontal = 16.dp),
                    trailingIconContent = {
                        if (userNameText.text.isNotEmpty()) {
                            IconButton(
                                onClick = { userNameText = TextFieldValue() }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_close_circle),
                                    modifier = Modifier.size(Space20),
                                    contentDescription = "close icon",
                                    tint = Gray200
                                )
                            }
                        }
                    }
                )
                Spacer(Modifier.width(Space12))
                ConfirmButton(
                    modifier = Modifier
                        .height(Space48)
                        .width(77.dp),
                    properties = ConfirmButtonProperties(
                        size = ConfirmButtonSize.Large,
                        type = ConfirmButtonType.Secondary
                    ),
                    onClick = {
                        //TODO : check user
                    }
                ) { style ->
                    Text(
                        text = stringResource(R.string.confirm_button_check_duplication),
                        style = style.merge(color = Gray600)
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
        }

    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is RegistrationNamingEvent.GoToNextStep -> {
                    appState.navController.navigate(RegistrationCollectingConstant.ROUTE)
                }
            }
        }
    }
}

@Preview
@Composable
fun RegistrationNamingScreenPreview() {
    RegistrationNamingScreen(
        appState = rememberApplicationState(),
        model = RegistrationNamingModel(
            state = RegistrationNamingState.Init
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
