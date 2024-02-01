package ac.dnd.bookkeeping.android.domain.model.history

import ac.dnd.bookkeeping.android.domain.model.event.Group

data class HistoryInfo(
    val unwrittenCount: Int,
    val totalHeartCount: Int,
    val groups: List<Group>
)
