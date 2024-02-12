package ac.dnd.bookkeeping.android.presentation.ui.main.home.schedule.add

import ac.dnd.bookkeeping.android.presentation.ui.main.home.schedule.ScheduleConstant

object ScheduleAddConstant {
    const val ROUTE: String = "${ScheduleConstant.ROUTE}/add"

    const val ROUTE_ARGUMENT_SCHEDULE_ID = "id"
    const val ROUTE_STRUCTURE = "$ROUTE?$ROUTE_ARGUMENT_SCHEDULE_ID={$ROUTE_ARGUMENT_SCHEDULE_ID}"
}
