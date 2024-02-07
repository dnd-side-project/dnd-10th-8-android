package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.relation

sealed interface RelationEvent {
    sealed interface AddRelation : RelationEvent {
        data object Success : AddRelation
    }

    sealed interface EditRelation : RelationEvent {
        data object Success : EditRelation
    }

    sealed interface DeleteRelation : RelationEvent {
        data object Success : DeleteRelation
    }
}
