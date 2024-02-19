package ac.dnd.mour.android.presentation.ui.main.home.common.group.get

sealed interface GetGroupState {
    data object Init : GetGroupState
    data object Loading : GetGroupState
}
