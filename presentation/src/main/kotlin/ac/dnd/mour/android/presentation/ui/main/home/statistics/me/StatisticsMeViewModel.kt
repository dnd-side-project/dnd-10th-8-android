package ac.dnd.mour.android.presentation.ui.main.home.statistics.me

import ac.dnd.mour.android.domain.model.error.ServerException
import ac.dnd.mour.android.domain.model.feature.statistics.MyStatistics
import ac.dnd.mour.android.domain.usecase.feature.statistics.GetMyStatisticsUseCase
import ac.dnd.mour.android.presentation.common.base.BaseViewModel
import ac.dnd.mour.android.presentation.common.base.ErrorEvent
import ac.dnd.mour.android.common.coroutine.event.EventFlow
import ac.dnd.mour.android.common.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.common.coroutine.event.asEventFlow
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.todayIn

@HiltViewModel
class StatisticsMeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getMyStatisticsUseCase: GetMyStatisticsUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<StatisticsMeState> =
        MutableStateFlow(StatisticsMeState.Init)
    val state: StateFlow<StatisticsMeState> = _state.asStateFlow()

    private val _event: MutableEventFlow<StatisticsMeEvent> = MutableEventFlow()
    val event: EventFlow<StatisticsMeEvent> = _event.asEventFlow()

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

    fun onIntent(intent: StatisticsMeIntent) {
        when (intent) {
            is StatisticsMeIntent.OnClickDateChange -> {
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
            _state.value = StatisticsMeState.Loading
            getMyStatisticsUseCase(
                year = year,
                month = month
            ).onSuccess {
                _state.value = StatisticsMeState.Init
                _myStatistics.value = it
            }.onFailure { exception ->
                _state.value = StatisticsMeState.Init
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
