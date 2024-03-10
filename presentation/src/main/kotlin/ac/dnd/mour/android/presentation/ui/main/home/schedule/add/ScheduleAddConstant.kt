package ac.dnd.mour.android.presentation.ui.main.home.schedule.add

import ac.dnd.mour.android.presentation.ui.main.home.schedule.ScheduleConstant

object ScheduleAddConstant {
    const val ROUTE: String = "${ScheduleConstant.ROUTE}/add"

    const val ROUTE_ARGUMENT_SCHEDULE_ID = "id"
    const val ROUTE_ARGUMENT_SCHEDULE_YEAR = "year"
    const val ROUTE_ARGUMENT_SCHEDULE_MONTH = "month"
    const val ROUTE_ARGUMENT_SCHEDULE_DAY = "day"
    const val ROUTE_STRUCTURE =
        "${ROUTE}?${ROUTE_ARGUMENT_SCHEDULE_ID}={${ROUTE_ARGUMENT_SCHEDULE_ID}}&" +
                "${ROUTE_ARGUMENT_SCHEDULE_YEAR}={${ROUTE_ARGUMENT_SCHEDULE_YEAR}}&" +
                "${ROUTE_ARGUMENT_SCHEDULE_MONTH}={${ROUTE_ARGUMENT_SCHEDULE_MONTH}}&" +
                "${ROUTE_ARGUMENT_SCHEDULE_DAY}={${ROUTE_ARGUMENT_SCHEDULE_DAY}}"
}
