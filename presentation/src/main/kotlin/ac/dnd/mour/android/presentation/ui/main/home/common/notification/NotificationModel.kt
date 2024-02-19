package ac.dnd.mour.android.presentation.ui.main.home.common.notification

import ac.dnd.mour.android.domain.model.feature.schedule.Alarm

data class NotificationModel(
    val state: NotificationState,
    val alarmList: List<Alarm>
)
