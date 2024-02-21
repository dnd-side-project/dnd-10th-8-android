package ac.dnd.mour.android.presentation.ui.main.home.history.detail.growth

import ac.dnd.mour.android.presentation.common.base.BaseViewModel
import ac.dnd.mour.android.common.coroutine.event.EventFlow
import ac.dnd.mour.android.common.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.common.coroutine.event.asEventFlow
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class HistoryDetailGrowthViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<HistoryDetailGrowthState> =
        MutableStateFlow(HistoryDetailGrowthState.Init)
    val state: StateFlow<HistoryDetailGrowthState> = _state.asStateFlow()

    private val _event: MutableEventFlow<HistoryDetailGrowthEvent> = MutableEventFlow()
    val event: EventFlow<HistoryDetailGrowthEvent> = _event.asEventFlow()

    fun onIntent(intent: HistoryDetailGrowthIntent) {

    }
}
