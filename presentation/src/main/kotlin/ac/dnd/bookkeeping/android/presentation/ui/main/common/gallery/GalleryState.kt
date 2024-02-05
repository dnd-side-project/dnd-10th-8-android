package ac.dnd.bookkeeping.android.presentation.ui.main.common.gallery

sealed interface GalleryState {
    data object Init : GalleryState
}
