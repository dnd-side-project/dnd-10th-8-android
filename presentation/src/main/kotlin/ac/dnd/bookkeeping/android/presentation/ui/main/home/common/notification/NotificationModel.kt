package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.notification

import ac.dnd.bookkeeping.android.domain.model.feature.schedule.Alarm

data class NotificationModel(
    val state: NotificationState,
    val alarmList: List<Alarm>
)
