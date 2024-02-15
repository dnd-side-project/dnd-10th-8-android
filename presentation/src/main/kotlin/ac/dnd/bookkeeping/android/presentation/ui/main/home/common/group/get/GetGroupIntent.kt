package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.group.get

import ac.dnd.bookkeeping.android.domain.model.feature.group.Group

sealed interface GetGroupIntent {
    data class OnDelete(val id: Long) : GetGroupIntent
    data class OnEdit(val group: Group) : GetGroupIntent
    data class OnAdd(val group: Group) : GetGroupIntent
}
