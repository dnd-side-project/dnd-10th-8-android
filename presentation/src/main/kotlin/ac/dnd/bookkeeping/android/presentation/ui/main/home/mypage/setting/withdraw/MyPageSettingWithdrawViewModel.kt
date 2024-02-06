package ac.dnd.bookkeeping.android.presentation.ui.main.home.mypage.setting.withdraw

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
class MyPageSettingWithdrawViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<MyPageSettingWithdrawState> =
        MutableStateFlow(MyPageSettingWithdrawState.Init)
    val state: StateFlow<MyPageSettingWithdrawState> = _state.asStateFlow()

    private val _event: MutableEventFlow<MyPageSettingWithdrawEvent> = MutableEventFlow()
    val event: EventFlow<MyPageSettingWithdrawEvent> = _event.asEventFlow()

    fun onIntent(intent: MyPageSettingWithdrawIntent) {

    }
}
