package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.relation.add

import ac.dnd.bookkeeping.android.domain.model.error.ServerException

sealed interface AddRelationEvent {
    sealed interface Submit : AddRelationEvent {
        data object Success : Submit
    }
}
