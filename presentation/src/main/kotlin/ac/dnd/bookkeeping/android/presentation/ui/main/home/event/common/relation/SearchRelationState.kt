package ac.dnd.bookkeeping.android.presentation.ui.main.home.event.common.relation

sealed interface SearchRelationState {
    data object Init : SearchRelationState
    data object Loading : SearchRelationState
}
