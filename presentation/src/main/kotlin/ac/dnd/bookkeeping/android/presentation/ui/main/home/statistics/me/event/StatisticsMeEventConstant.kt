package ac.dnd.bookkeeping.android.presentation.ui.main.home.statistics.me.event

import ac.dnd.bookkeeping.android.presentation.ui.main.home.statistics.me.StatisticsMeConstant

object StatisticsMeEventConstant {
    const val ROUTE: String = "${StatisticsMeConstant.ROUTE}/event"

    const val ROUTE_ARGUMENT_EVENT_ID = "id"
    const val ROUTE_ARGUMENT_IS_GIVE = "isGive"
    const val ROUTE_STRUCTURE =
        "$ROUTE?$ROUTE_ARGUMENT_EVENT_ID={$ROUTE_ARGUMENT_EVENT_ID}&$ROUTE_ARGUMENT_IS_GIVE={$ROUTE_ARGUMENT_IS_GIVE}"
}
