package ac.dnd.mour.android.presentation.ui.main.common.gallery

sealed interface GalleryState {
    data object Init : GalleryState
}
