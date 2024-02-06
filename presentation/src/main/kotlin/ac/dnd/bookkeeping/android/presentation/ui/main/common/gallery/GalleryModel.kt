package ac.dnd.bookkeeping.android.presentation.ui.main.common.gallery

import ac.dnd.bookkeeping.android.domain.model.gallery.GalleryFolder
import ac.dnd.bookkeeping.android.domain.model.gallery.GalleryImage
import androidx.paging.compose.LazyPagingItems

class GalleryModel(
    val state: GalleryState,
    val folders: List<GalleryFolder>,
    val currentFolder: GalleryFolder,
    val galleryImages: LazyPagingItems<GalleryImage>
)
