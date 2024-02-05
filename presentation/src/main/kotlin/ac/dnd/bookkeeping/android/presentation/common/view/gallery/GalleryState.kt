package ac.dnd.bookkeeping.android.presentation.common.view.gallery

sealed interface GalleryState {
    data object Init : GalleryState
}
