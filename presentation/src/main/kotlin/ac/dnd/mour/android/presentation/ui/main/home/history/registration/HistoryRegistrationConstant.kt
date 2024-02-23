package ac.dnd.mour.android.presentation.ui.main.home.history.registration

import ac.dnd.mour.android.presentation.ui.main.home.history.HistoryConstant

object HistoryRegistrationConstant {
    const val ROUTE: String = "${HistoryConstant.ROUTE}/registration"

    const val ROUTE_ARGUMENT_ID = "id"
    const val ROUTE_ARGUMENT_NAME = "name"
    const val ROUTE_ARGUMENT_IS_HOME = "isHome"
    const val CONTAIN_RELATION_MODEL =
        "${ROUTE}?${ROUTE_ARGUMENT_ID}={${ROUTE_ARGUMENT_ID}}&${ROUTE_ARGUMENT_NAME}={${ROUTE_ARGUMENT_NAME}}" +
                "&${ROUTE_ARGUMENT_IS_HOME}={${ROUTE_ARGUMENT_IS_HOME}}"
}
