package ac.dnd.mour.android.presentation.ui.main.home.common.group.edit

import ac.dnd.mour.android.domain.model.feature.group.Group

sealed interface EditGroupEvent {
    sealed interface EditGroup : EditGroupEvent {
        data class Success(val group: Group) : EditGroup
    }
}
