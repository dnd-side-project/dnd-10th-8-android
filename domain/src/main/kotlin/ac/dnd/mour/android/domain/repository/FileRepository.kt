package ac.dnd.mour.android.domain.repository

import ac.dnd.mour.android.domain.model.file.PreSignedUrl

interface FileRepository {
    suspend fun getPreSignedUrl(
        fileName: String
    ): Result<PreSignedUrl>

    suspend fun upload(
        preSignedUrl: String,
        imageUri: String
    ): Result<Unit>
}
