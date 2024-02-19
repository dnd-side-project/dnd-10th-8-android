package ac.dnd.mour.android.presentation.ui.main.splash

import ac.dnd.mour.android.domain.model.error.ServerException
import ac.dnd.mour.android.domain.model.feature.schedule.Alarm

sealed interface SplashEvent {
    sealed interface Login : SplashEvent {
        data class Success(val alarmList: List<Alarm>) : Login
        data class Failure(val exception: ServerException) : Login
    }
}
