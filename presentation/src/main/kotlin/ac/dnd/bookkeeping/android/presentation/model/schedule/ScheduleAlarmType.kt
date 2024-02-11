package ac.dnd.bookkeeping.android.presentation.model.schedule

sealed class ScheduleAlarmType(
    val text: String
) {
    data object None : ScheduleAlarmType(text = "알림 없음")
    data object TodayNine : ScheduleAlarmType(text = "당일 9:00AM")
    data object TodayTwelve : ScheduleAlarmType(text = "당일 12:00AM")
    data object YesterdayNine : ScheduleAlarmType(text = "1일 전 9:00AM")
    data object TwoDaysAgoNine : ScheduleAlarmType(text = "2일 전 9:00AM")
    data object OneWeekAgoNine : ScheduleAlarmType(text = "1주 전 9:00AM")

    companion object {
        fun getAll(): List<ScheduleAlarmType> {
            return listOf(
                None,
                TodayNine,
                TodayTwelve,
                YesterdayNine,
                TwoDaysAgoNine,
                OneWeekAgoNine
            )
        }
    }
}
