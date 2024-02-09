package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.relation

sealed interface RelationState {
    data object Init : RelationState
    data object Loading : RelationState
}
