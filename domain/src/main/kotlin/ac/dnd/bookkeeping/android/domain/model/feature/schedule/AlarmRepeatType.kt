package ac.dnd.bookkeeping.android.domain.model.feature.schedule

sealed class AlarmRepeatType(val value: String) {
    data object Month : AlarmRepeatType("month")
    data object Year : AlarmRepeatType("year")
}
