package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.unrecorded

import ac.dnd.bookkeeping.android.domain.model.feature.schedule.UnrecordedSchedule

data class HistoryUnrecordedModel(
    val state: HistoryUnrecordedState,
    val unrecordedList: List<UnrecordedSchedule>
)
