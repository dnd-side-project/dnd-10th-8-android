package ac.dnd.bookkeeping.android.domain.model.gallery

data class GalleryImage(
    val id: Long,
    val filePath: String,
    val name: String,
    val date: String,
    val size: Int,
)
