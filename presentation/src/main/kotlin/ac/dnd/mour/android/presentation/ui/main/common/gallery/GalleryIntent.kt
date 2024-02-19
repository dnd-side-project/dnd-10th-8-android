package ac.dnd.mour.android.presentation.ui.main.common.gallery

import ac.dnd.mour.android.domain.model.gallery.GalleryFolder

sealed interface GalleryIntent {
    data object OnGrantPermission : GalleryIntent
    data class OnChangeFolder(
        val location: GalleryFolder
    ) : GalleryIntent
}
