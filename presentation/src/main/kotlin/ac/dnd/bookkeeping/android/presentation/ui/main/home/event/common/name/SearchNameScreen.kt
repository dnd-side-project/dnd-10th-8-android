package ac.dnd.bookkeeping.android.presentation.ui.main.home.event.common.name

import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline1
import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.common.view.BottomSheetScreen
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.HomeConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.login.LoginConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.rememberApplicationState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import kotlinx.coroutines.CoroutineExceptionHandler

@Composable
fun SearchNameScreen(
    appState: ApplicationState,
    model: SearchNameModel,
    event: EventFlow<SearchNameEvent>,
    intent: (SearchNameIntent) -> Unit,
    handler: CoroutineExceptionHandler
) {
    fun navigateToLogin() {
        appState.navController.navigate(LoginConstant.ROUTE) {
            popUpTo(SearchNameConstant.ROUTE) {
                inclusive = true
            }
        }
    }

    fun navigateToHome() {
        appState.navController.navigate(HomeConstant.ROUTE) {
            popUpTo(SearchNameConstant.ROUTE) {
                inclusive = true
            }
        }
    }

    fun login(event: SearchNameEvent.Login) {
        when (event) {
            SearchNameEvent.Login.Success -> {
                navigateToHome()
            }

            is SearchNameEvent.Login.Failure -> {
                navigateToLogin()
            }

            is SearchNameEvent.Login.Error -> {
                // TODO : Unknown Error (Client Error, Internal Server Error, ...)
            }
        }
    }

    BottomSheetScreen(
        onDismissRequest = { /*TODO*/ },
    ) {

    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = R.drawable.ic_launcher),
            contentDescription = ""
        )
        Text(
            text = stringResource(id = R.string.app_name),
            style = Headline1
        )
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is SearchNameEvent.Login -> login(event)
            }
        }
    }
}

@Preview
@Composable
fun SearchNameScreenPreview() {
    SearchNameScreen(
        appState = rememberApplicationState(),
        model = SearchNameModel(state = SearchNameState.Init),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
