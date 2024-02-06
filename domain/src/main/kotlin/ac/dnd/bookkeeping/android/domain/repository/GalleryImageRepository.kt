package ac.dnd.bookkeeping.android.domain.repository

import ac.dnd.bookkeeping.android.domain.model.gallery.GalleryFolder
import ac.dnd.bookkeeping.android.domain.model.gallery.GalleryImage

interface GalleryImageRepository {

    fun getPhotoList(
        page: Int,
        loadSize: Int,
        currentLocation: String? = null
    ): List<GalleryImage>

    fun getFolderList(): List<GalleryFolder>
}
