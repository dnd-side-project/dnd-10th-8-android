package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.detail

import ac.dnd.bookkeeping.android.presentation.ui.main.home.history.HistoryConstant

object HistoryDetailConstant {
    const val ROUTE: String = "${HistoryConstant.ROUTE}/detail"

    const val ROUTE_ARGUMENT_ID = "id"
    const val CONTAIN_ID_MODEL = "$ROUTE?$ROUTE_ARGUMENT_ID={$ROUTE_ARGUMENT_ID}"
}
