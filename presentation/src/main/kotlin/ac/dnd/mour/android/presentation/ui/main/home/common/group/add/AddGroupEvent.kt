package ac.dnd.mour.android.presentation.ui.main.home.common.group.add

import ac.dnd.mour.android.domain.model.feature.group.Group

sealed interface AddGroupEvent {
    sealed interface AddGroup : AddGroupEvent {
        data class Success(val group: Group) : AddGroup
    }
}
