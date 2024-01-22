package ac.dnd.bookkeeping.android.presentation.common.util.coroutine

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

// TODO : 고도화하기
suspend fun <T1, T2> zip(
    block1: suspend () -> Result<T1>,
    block2: suspend () -> Result<T2>
): Result<Pair<T1, T2>> = withContext(Dispatchers.IO) {
    val deferred1 = async { block1() }
    val deferred2 = async { block2() }

    return@withContext runCatching {
        deferred1.await().getOrThrow() to deferred2.await().getOrThrow()
    }
}

fun LifecycleOwner.repeatOnStarted(
    context: CoroutineContext,
    block: suspend CoroutineScope.() -> Unit
) {
    this.lifecycleScope.launch(context) {
        repeatOnLifecycle(Lifecycle.State.STARTED, block)
    }
}
