package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.relation.add

sealed interface AddRelationState{
    data object Init : AddRelationState
    data object Loading : AddRelationState
}
