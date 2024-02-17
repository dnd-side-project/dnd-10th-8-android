package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.detail.growth

import ac.dnd.bookkeeping.android.presentation.ui.main.home.history.detail.HistoryDetailConstant

object HistoryDetailGrowthConstant {
    const val ROUTE: String = "${HistoryDetailConstant.ROUTE}/growth"

    const val ROUTE_ARGUMENT_TOTAL_MONEY = "totalPrice"
    const val CONTAIN_MONEY_MODEL =
        "${ROUTE}?${ROUTE_ARGUMENT_TOTAL_MONEY}={${ROUTE_ARGUMENT_TOTAL_MONEY}}"
}
