package ac.dnd.mour.android.presentation.ui.main.home.common.notification

sealed interface NotificationState {
    data object Init : NotificationState
}
