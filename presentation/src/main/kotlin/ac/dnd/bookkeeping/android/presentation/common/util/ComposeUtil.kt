package ac.dnd.bookkeeping.android.presentation.common.util

import ac.dnd.bookkeeping.android.presentation.common.base.BaseViewModel
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.common.view.DialogScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
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
    val dialogIsShowingState = remember { mutableStateOf(false) }
    DialogScreen(dialogIsShowingState)

    LaunchedEffectWithLifecycle(viewModel.errorEvent) {
        viewModel.errorEvent.eventObserve { event ->
            Timber.d(event.throwable)
            Sentry.captureException(event.throwable)

            dialogIsShowingState.value = true
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
