package ac.dnd.mour.android.presentation.ui.main.home.statistics.user

import ac.dnd.mour.android.domain.model.error.ServerException
import ac.dnd.mour.android.domain.model.feature.statistics.GroupStatistics
import ac.dnd.mour.android.domain.usecase.feature.statistics.GetGroupStatisticsUseCase
import ac.dnd.mour.android.presentation.common.base.BaseViewModel
import ac.dnd.mour.android.presentation.common.base.ErrorEvent
import ac.dnd.mour.android.common.coroutine.event.EventFlow
import ac.dnd.mour.android.common.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.common.coroutine.event.asEventFlow
import ac.dnd.mour.android.presentation.ui.main.registration.main.type.UserGender
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class StatisticsUserViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getGroupStatisticsUseCase: GetGroupStatisticsUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<StatisticsUserState> =
        MutableStateFlow(StatisticsUserState.Init)
    val state: StateFlow<StatisticsUserState> = _state.asStateFlow()

    private val _event: MutableEventFlow<StatisticsUserEvent> = MutableEventFlow()
    val event: EventFlow<StatisticsUserEvent> = _event.asEventFlow()

    private val _groupStatistics: MutableStateFlow<List<GroupStatistics>> =
        MutableStateFlow(emptyList())
    val groupStatistics: StateFlow<List<GroupStatistics>> = _groupStatistics.asStateFlow()

    init {
        refresh(
            age = 20,
            gender = UserGender.Female
        )
    }

    fun onIntent(intent: StatisticsUserIntent) {
        when (intent) {
            is StatisticsUserIntent.OnChangeGroup -> {
                refresh(
                    age = intent.age,
                    gender = intent.gender
                )
            }
        }
    }

    private fun refresh(
        age: Int,
        gender: UserGender
    ) {
        launch {
            _state.value = StatisticsUserState.Loading
            getGroupStatisticsUseCase(
                range = age,
                gender = when (gender) {
                    UserGender.Female -> "female"
                    UserGender.Male -> "male"
                }
            ).onSuccess {
                _state.value = StatisticsUserState.Init
                _groupStatistics.value = it
            }.onFailure { exception ->
                _state.value = StatisticsUserState.Init
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
