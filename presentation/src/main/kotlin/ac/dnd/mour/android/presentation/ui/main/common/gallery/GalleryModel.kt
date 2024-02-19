package ac.dnd.mour.android.presentation.ui.main.common.gallery

import ac.dnd.mour.android.domain.model.gallery.GalleryFolder
import ac.dnd.mour.android.domain.model.gallery.GalleryImage
import androidx.paging.compose.LazyPagingItems

class GalleryModel(
    val state: GalleryState,
    val folders: List<GalleryFolder>,
    val currentFolder: GalleryFolder,
    val galleryImages: LazyPagingItems<GalleryImage>
)
