package ac.dnd.mour.android.domain.repository

import ac.dnd.mour.android.domain.model.gallery.GalleryFolder
import ac.dnd.mour.android.domain.model.gallery.GalleryImage
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface GalleryRepository {

    fun getPagingGalleryList(
        folder: GalleryFolder
    ): Flow<PagingData<GalleryImage>>

    fun getFolderList(): List<GalleryFolder>

}
