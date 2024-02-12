package ac.dnd.bookkeeping.android.domain.model.feature.schedule

sealed class AlarmRepeatType(val value: String) {
    data object Month : AlarmRepeatType("month")
    data object Year : AlarmRepeatType("year")

    companion object {
        fun getAll(): List<AlarmRepeatType> {
            return listOf(
                Month,
                Year
            )
        }

        fun fromValue(value: String): AlarmRepeatType? {
            return getAll().find { it.value == value }
        }
    }
}
