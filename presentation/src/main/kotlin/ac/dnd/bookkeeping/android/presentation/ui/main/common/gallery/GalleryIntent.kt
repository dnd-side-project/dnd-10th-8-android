package ac.dnd.bookkeeping.android.presentation.ui.main.common.gallery

import ac.dnd.bookkeeping.android.domain.model.gallery.GalleryFolder

sealed interface GalleryIntent {
    data object OnGrantPermission : GalleryIntent
    data class OnChangeFolder(
        val location: GalleryFolder
    ) : GalleryIntent
}
