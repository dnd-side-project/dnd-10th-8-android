package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.group.edit

import ac.dnd.bookkeeping.android.domain.model.feature.group.Group

sealed interface EditGroupEvent {
    sealed interface EditGroup : EditGroupEvent {
        data class Success(val group: Group) : EditGroup
    }
}
