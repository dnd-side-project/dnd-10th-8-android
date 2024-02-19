package ac.dnd.mour.android.presentation.common.base

import ac.dnd.mour.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.asEventFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    val handler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            _errorEvent.emit(ErrorEvent.Client(throwable))
        }
    }

    protected val _errorEvent: MutableEventFlow<ErrorEvent> = MutableEventFlow()
    val errorEvent: EventFlow<ErrorEvent> = _errorEvent.asEventFlow()

    fun launch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(handler, block = block)
    }

    protected inline fun <T, R> StateFlow<T>.map(
        defaultValue: R,
        crossinline transform: suspend (value: T) -> R,
    ): StateFlow<R> {
        return map(transform).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            defaultValue
        )
    }
}
