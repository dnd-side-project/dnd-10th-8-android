package ac.dnd.mour.android.presentation.common.util

import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.base.BaseViewModel
import ac.dnd.mour.android.presentation.common.base.ErrorEvent
import ac.dnd.mour.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.mour.android.presentation.common.view.DialogScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import io.sentry.Sentry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@Composable
fun ErrorObserver(
    viewModel: BaseViewModel
) {
    var _error: ErrorEvent? by remember { mutableStateOf(null) }
    val error = _error

    when (error) {
        is ErrorEvent.Client -> {
            DialogScreen(
                title = stringResource(id = R.string.error_dialog_client_title),
                message = stringResource(id = R.string.error_dialog_client_content),
                onDismissRequest = {
                    _error = null
                }
            )
        }

        is ErrorEvent.InvalidRequest -> {
            DialogScreen(
                title = stringResource(id = R.string.error_dialog_invalid_request_title),
                message = error.exception.message,
                onDismissRequest = {
                    _error = null
                }
            )
        }

        is ErrorEvent.UnavailableServer -> {
            DialogScreen(
                title = stringResource(id = R.string.error_dialog_unavailable_server_title),
                message = stringResource(id = R.string.error_dialog_unavailable_server_content),
                onDismissRequest = {
                    _error = null
                }
            )
        }

        else -> Unit
    }

    LaunchedEffectWithLifecycle(viewModel.errorEvent) {
        viewModel.errorEvent.eventObserve { event ->
            _error = event
            Timber.d(event.exception)
            Sentry.captureException(event.exception)
        }
    }
}

@Composable
fun LaunchedEffectWithLifecycle(
    key1: Any?,
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1) {
        lifecycleOwner.lifecycleScope.launch(
            coroutineContext + context
        ) {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED, block)
        }
    }
}
