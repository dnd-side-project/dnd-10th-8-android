package ac.dnd.mour.android.presentation.ui.main.home.common.group.add

sealed interface AddGroupState {
    data object Init : AddGroupState
    data object Loading : AddGroupState
}
