package ac.dnd.mour.android.presentation.ui.main.home

object HomeConstant {
    const val ROUTE = "/home"

    const val ROUTE_ARGUMENT_MESSAGE = "message"
    const val ROUTE_STRUCTURE = "${ROUTE}?$ROUTE_ARGUMENT_MESSAGE={$ROUTE_ARGUMENT_MESSAGE}"
}
