package ac.dnd.mour.android.presentation.ui.main.home

import ac.dnd.mour.android.presentation.common.base.BaseViewModel
import ac.dnd.mour.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.asEventFlow
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState.Init)
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _event: MutableEventFlow<HomeEvent> = MutableEventFlow()
    val event: EventFlow<HomeEvent> = _event.asEventFlow()

    val message: String by lazy {
        savedStateHandle.get<String>(HomeConstant.ROUTE_ARGUMENT_MESSAGE) ?: ""
    }

    fun onIntent(intent: HomeIntent) {

    }

    fun viewMessage(message: String) {
        launch {
            _event.emit(HomeEvent.ShowSnackBar(message))
        }
    }
}
