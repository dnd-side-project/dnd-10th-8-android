package ac.dnd.mour.android.presentation.ui.main

import ac.dnd.mour.android.common.coroutine.event.EventFlow
import ac.dnd.mour.android.domain.usecase.authentication.GetTokenRefreshFailEventFlowUseCase
import ac.dnd.mour.android.presentation.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTokenRefreshFailEventFlowUseCase: GetTokenRefreshFailEventFlowUseCase
) : BaseViewModel() {

    val refreshFailEvent: EventFlow<Unit> = getTokenRefreshFailEventFlowUseCase()

}
