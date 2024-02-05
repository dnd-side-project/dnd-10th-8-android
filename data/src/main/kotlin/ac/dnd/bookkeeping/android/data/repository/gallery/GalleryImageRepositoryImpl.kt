package ac.dnd.bookkeeping.android.data.repository.gallery

import ac.dnd.bookkeeping.android.data.remote.gallery.GalleryPagingSource
import ac.dnd.bookkeeping.android.domain.model.gallery.GalleryImage
import ac.dnd.bookkeeping.android.domain.repository.GalleryImageRepository
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.os.bundleOf
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class GalleryImageRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : GalleryImageRepository {

    private val uriExternal: Uri by lazy {
        MediaStore.Images.Media.getContentUri(
            MediaStore.VOLUME_EXTERNAL
        )
    }

    private val projection = arrayOf(
        MediaStore.Images.ImageColumns.DATA,
        MediaStore.Images.ImageColumns.DISPLAY_NAME,
        MediaStore.Images.ImageColumns.DATE_TAKEN,
        MediaStore.Images.ImageColumns._ID
    )

    private val contentResolver by lazy { context.contentResolver }
    private val sortedOrder = MediaStore.Images.ImageColumns.DATE_TAKEN

    override fun getPagingPhotos(folder: Pair<String, String?>): Flow<PagingData<GalleryImage>> {
        return Pager(
            config = PagingConfig(
                pageSize = GalleryPagingSource.PAGING_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                GalleryPagingSource(
                    imageRepository = this,
                    currentLocation = folder.second,
                )
            },
        ).flow
    }

    override fun getAllPhotos(
        page: Int,
        loadSize: Int,
        currentLocation: String?
    ): List<GalleryImage> {
        val galleryImageList = mutableListOf<GalleryImage>()

        var selection: String? = null
        var selectionArgs: Array<String>? = null
        if (currentLocation != null) {
            selection = "${MediaStore.Images.Media.DATA} LIKE ?"
            selectionArgs = arrayOf("%$currentLocation%")
        }

        val offset = (page - 1) * loadSize
        val query = getQuery(offset, loadSize, selection, selectionArgs)

        query?.use { cursor ->
            while (cursor.moveToNext()) {
                val id =
                    cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID))
                val name =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DISPLAY_NAME))
                val filePath =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA))
                val date =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATE_TAKEN))
                val contentUri = ContentUris.withAppendedId(uriExternal, id)
                val image = GalleryImage(
                    id = id,
                    filePath = filePath,
                    uri = contentUri,
                    name = name,
                    date = date ?: "",
                    size = 0,
                )
                galleryImageList.add(image)
            }
        }
        return galleryImageList
    }

    override fun getFolderList(): List<String> {
        val folderList: ArrayList<String> = arrayListOf("최근 항목")
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                val filePath = cursor.getString(columnIndex)
                val folder = File(filePath).parent
                folder?.let {
                    if (!folderList.contains(folder)) {
                        folderList.add(folder)
                    }
                }
            }
            cursor.close()
        }
        return folderList
    }

    @SuppressLint("Recycle")
    private fun getQuery(
        offset: Int,
        limit: Int,
        selection: String?,
        selectionArgs: Array<String>?,
    ) = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
        val bundle = bundleOf(
            ContentResolver.QUERY_ARG_OFFSET to offset,
            ContentResolver.QUERY_ARG_LIMIT to limit,
            ContentResolver.QUERY_ARG_SORT_COLUMNS to arrayOf(MediaStore.Files.FileColumns.DATE_MODIFIED),
            ContentResolver.QUERY_ARG_SORT_DIRECTION to ContentResolver.QUERY_SORT_DIRECTION_DESCENDING,
            ContentResolver.QUERY_ARG_SQL_SELECTION to selection,
            ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS to selectionArgs,
        )
        contentResolver.query(uriExternal, projection, bundle, null)
    } else {
        contentResolver.query(
            uriExternal,
            projection,
            selection,
            selectionArgs,
            "$sortedOrder DESC LIMIT $limit OFFSET $offset",
        )
    }
}
