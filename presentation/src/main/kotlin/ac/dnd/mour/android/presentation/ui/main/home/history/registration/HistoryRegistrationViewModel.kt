package ac.dnd.mour.android.presentation.ui.main.home.history.registration

import ac.dnd.mour.android.domain.model.error.ServerException
import ac.dnd.mour.android.domain.usecase.feature.heart.AddHeartUseCase
import ac.dnd.mour.android.presentation.common.base.BaseViewModel
import ac.dnd.mour.android.presentation.common.base.ErrorEvent
import ac.dnd.mour.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.asEventFlow
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.LocalDate
import timber.log.Timber

@HiltViewModel
class HistoryRegistrationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val addHeartUseCase: AddHeartUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<HistoryRegistrationState> =
        MutableStateFlow(HistoryRegistrationState.Init)
    val state: StateFlow<HistoryRegistrationState> = _state.asStateFlow()

    private val _event: MutableEventFlow<HistoryRegistrationEvent> = MutableEventFlow()
    val event: EventFlow<HistoryRegistrationEvent> = _event.asEventFlow()

    fun onIntent(intent: HistoryRegistrationIntent) {
        when (intent) {
            is HistoryRegistrationIntent.OnClickSubmit -> {
                registration(
                    relationId = intent.relationId,
                    give = intent.give,
                    money = intent.money,
                    day = intent.day,
                    event = intent.event,
                    memo = intent.memo,
                    tags = intent.tags
                )
            }
        }
    }

    private fun registration(
        relationId: Long,
        give: Boolean,
        money: Long,
        day: LocalDate,
        event: String,
        memo: String,
        tags: List<String>
    ) {
        Timber.d(relationId.toString())
        launch {
            _state.value = HistoryRegistrationState.Loading
            addHeartUseCase(
                relationId = relationId,
                give = give,
                money = money,
                day = day,
                event = event,
                memo = memo,
                tags = tags
            )
                .onSuccess {
                    Timber.d(it.toString())
                    _state.value = HistoryRegistrationState.Init
                    _event.emit(HistoryRegistrationEvent.Submit.Success)
                }
                .onFailure { exception ->
                    Timber.d(exception.message.toString())
                    _state.value = HistoryRegistrationState.Init
                    when (exception) {
                        is ServerException -> {
                            _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                        }

                        else -> {
                            _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                        }
                    }
                }
        }
    }
}
