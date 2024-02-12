package ac.dnd.bookkeeping.android.presentation.ui.main.home.schedule

import kotlinx.datetime.LocalDate

sealed interface ScheduleIntent {
    data class ChangeDate(
        val date: LocalDate
    ) : ScheduleIntent
}
