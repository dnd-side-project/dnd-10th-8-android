package ac.dnd.mour.android.domain.repository

import ac.dnd.mour.android.domain.model.gallery.GalleryFolder
import ac.dnd.mour.android.domain.model.gallery.GalleryImage

interface GalleryImageRepository {

    fun getPhotoList(
        page: Int,
        loadSize: Int,
        currentLocation: String? = null
    ): List<GalleryImage>

    fun getFolderList(): List<GalleryFolder>
}
