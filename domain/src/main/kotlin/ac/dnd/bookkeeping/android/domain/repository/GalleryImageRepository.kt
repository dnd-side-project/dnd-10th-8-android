package ac.dnd.bookkeeping.android.domain.repository

import androidx.paging.PagingData
import ac.dnd.bookkeeping.android.domain.model.gallery.GalleryImage
import kotlinx.coroutines.flow.Flow

interface GalleryImageRepository {

    fun getPagingPhotos(
        folder: Pair<String, String?>
    ): Flow<PagingData<GalleryImage>>

    fun getAllPhotos(
        page: Int,
        loadSize: Int,
        currentLocation: String? = null
    ): List<GalleryImage>

    fun getFolderList(): List<String>
}
