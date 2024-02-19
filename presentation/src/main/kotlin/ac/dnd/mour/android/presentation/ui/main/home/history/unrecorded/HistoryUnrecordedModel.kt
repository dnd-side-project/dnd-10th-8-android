package ac.dnd.mour.android.presentation.ui.main.home.history.unrecorded

import ac.dnd.mour.android.domain.model.feature.schedule.UnrecordedSchedule

data class HistoryUnrecordedModel(
    val state: HistoryUnrecordedState,
    val unrecordedList: List<UnrecordedSchedule>
)
