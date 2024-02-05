package ac.dnd.bookkeeping.android.presentation.common.view.gallery

import com.dnd_9th_3_android.gooding.model.record.GalleryImage

class GalleryModel(
    val state: GalleryState,
    val folders: List<Pair<String, String?>>,
    val currentFolder: Pair<String, String?>,
    val galleryImages: List<GalleryImage>
)
