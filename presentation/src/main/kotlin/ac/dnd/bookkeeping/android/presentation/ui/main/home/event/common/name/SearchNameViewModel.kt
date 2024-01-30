package ac.dnd.bookkeeping.android.presentation.ui.main.home.event.common.name

import ac.dnd.bookkeeping.android.presentation.common.base.BaseViewModel
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.asEventFlow
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchNameViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<SearchNameState> = MutableStateFlow(SearchNameState.Init)
    val state: StateFlow<SearchNameState> = _state.asStateFlow()

    private val _event: MutableEventFlow<SearchNameEvent> = MutableEventFlow()
    val event: EventFlow<SearchNameEvent> = _event.asEventFlow()

    fun onIntent(intent: SearchNameIntent) {

    }
}
