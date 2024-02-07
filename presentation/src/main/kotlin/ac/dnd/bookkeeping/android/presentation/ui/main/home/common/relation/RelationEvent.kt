package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.relation

sealed interface RelationEvent {
    sealed interface Submit : RelationEvent {
        data object Success : Submit
    }
}
