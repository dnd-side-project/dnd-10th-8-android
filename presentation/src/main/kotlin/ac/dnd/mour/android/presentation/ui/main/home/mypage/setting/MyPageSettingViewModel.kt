package ac.dnd.mour.android.presentation.ui.main.home.mypage.setting

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
class MyPageSettingViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<MyPageSettingState> =
        MutableStateFlow(MyPageSettingState.Init)
    val state: StateFlow<MyPageSettingState> = _state.asStateFlow()

    private val _event: MutableEventFlow<MyPageSettingEvent> = MutableEventFlow()
    val event: EventFlow<MyPageSettingEvent> = _event.asEventFlow()

    fun onIntent(intent: MyPageSettingIntent) {

    }
}
