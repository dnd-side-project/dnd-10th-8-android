package ac.dnd.mour.android.presentation.ui.main.home.history.detail.information

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
class HistoryDetailInformationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<HistoryDetailInformationState> =
        MutableStateFlow(HistoryDetailInformationState.Init)
    val state: StateFlow<HistoryDetailInformationState> = _state.asStateFlow()

    private val _event: MutableEventFlow<HistoryDetailInformationEvent> = MutableEventFlow()
    val event: EventFlow<HistoryDetailInformationEvent> = _event.asEventFlow()

    fun onIntent(intent: HistoryDetailInformationIntent) {

    }
}
