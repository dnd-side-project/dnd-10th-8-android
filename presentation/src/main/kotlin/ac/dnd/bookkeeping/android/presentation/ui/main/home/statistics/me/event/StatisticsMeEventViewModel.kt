package ac.dnd.bookkeeping.android.presentation.ui.main.home.statistics.me.event

import ac.dnd.bookkeeping.android.domain.model.error.ServerException
import ac.dnd.bookkeeping.android.domain.model.feature.statistics.MyStatistics
import ac.dnd.bookkeeping.android.domain.usecase.feature.statistics.GetMyStatisticsUseCase
import ac.dnd.bookkeeping.android.presentation.common.base.BaseViewModel
import ac.dnd.bookkeeping.android.presentation.common.base.ErrorEvent
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.asEventFlow
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.todayIn
import javax.inject.Inject

@HiltViewModel
class StatisticsMeEventViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getMyStatisticsUseCase: GetMyStatisticsUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<StatisticsMeEventState> =
        MutableStateFlow(StatisticsMeEventState.Init)
    val state: StateFlow<StatisticsMeEventState> = _state.asStateFlow()

    private val _event: MutableEventFlow<StatisticsMeEventEvent> = MutableEventFlow()
    val event: EventFlow<StatisticsMeEventEvent> = _event.asEventFlow()

    val eventId: Long by lazy {
        savedStateHandle.get<Long>(StatisticsMeEventConstant.ROUTE_ARGUMENT_EVENT_ID) ?: -1L
    }

    val isGive: Boolean by lazy {
        savedStateHandle.get<Boolean>(StatisticsMeEventConstant.ROUTE_ARGUMENT_IS_GIVE) ?: true
    }

    private val now = Clock.System.todayIn(TimeZone.currentSystemDefault())

    private val _myStatistics: MutableStateFlow<MyStatistics> = MutableStateFlow(MyStatistics.empty)
    val myStatistics: StateFlow<MyStatistics> = _myStatistics.asStateFlow()

    init {
        launch {
            refresh(
                year = now.year,
                month = now.month.number
            )
        }
    }

    fun onIntent(intent: StatisticsMeEventIntent) {
        when (intent) {
            is StatisticsMeEventIntent.OnClickDateChange -> {
                refresh(
                    year = intent.year,
                    month = intent.month
                )
            }
        }
    }

    private fun refresh(
        year: Int,
        month: Int
    ) {
        launch {
            _state.value = StatisticsMeEventState.Loading
            getMyStatisticsUseCase(
                year = year,
                month = month
            ).onSuccess {
                _state.value = StatisticsMeEventState.Init
                _myStatistics.value = it
            }.onFailure { exception ->
                _state.value = StatisticsMeEventState.Init
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
