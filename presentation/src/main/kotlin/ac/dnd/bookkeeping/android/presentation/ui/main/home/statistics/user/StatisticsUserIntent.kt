package ac.dnd.bookkeeping.android.presentation.ui.main.home.statistics.user

import ac.dnd.bookkeeping.android.presentation.ui.main.registration.main.type.UserGender

sealed interface StatisticsUserIntent {
    data class OnChangeGroup(
        val age: Int,
        val gender: UserGender
    ) : StatisticsUserIntent
}
