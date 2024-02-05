package ac.dnd.bookkeeping.android.domain.model.gallery

import android.net.Uri

data class GalleryImage(
    val id: Long,
    val filePath: String,
    val uri: Uri,
    val name: String,
    val date: String,
    val size: Int,
)
