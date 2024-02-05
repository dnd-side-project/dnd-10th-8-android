package ac.dnd.bookkeeping.android.presentation.ui.main.common.gallery

import androidx.paging.compose.LazyPagingItems
import ac.dnd.bookkeeping.android.domain.model.gallery.GalleryImage

class GalleryModel(
    val state: GalleryState,
    val folders: List<Pair<String, String?>>,
    val currentFolder: Pair<String, String?>,
    val galleryImages: LazyPagingItems<GalleryImage>
)
