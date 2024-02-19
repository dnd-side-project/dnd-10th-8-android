package ac.dnd.mour.android.presentation.ui.main.home.common.relation.get

sealed interface GetRelationState {
    data object Init : GetRelationState
    data object Loading : GetRelationState
}
