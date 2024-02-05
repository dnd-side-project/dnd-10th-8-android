package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.relation.add

sealed interface AddRelationEvent {
    sealed interface Submit : AddRelationEvent {
        data object Success : Submit
    }
}
