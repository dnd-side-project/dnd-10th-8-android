package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.group.add

import ac.dnd.bookkeeping.android.domain.model.feature.group.Group

sealed interface AddGroupEvent {
    sealed interface AddGroup : AddGroupEvent {
        data class Success(val group: Group) : AddGroup
    }
}
