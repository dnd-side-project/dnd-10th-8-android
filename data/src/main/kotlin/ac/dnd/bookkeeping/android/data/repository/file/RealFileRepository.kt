package ac.dnd.bookkeeping.android.data.repository.file

import ac.dnd.bookkeeping.android.data.remote.local.SharedPreferencesManager
import ac.dnd.bookkeeping.android.data.remote.network.api.FileApi
import ac.dnd.bookkeeping.android.data.remote.network.util.toDomain
import ac.dnd.bookkeeping.android.domain.model.file.PreSignedUrl
import ac.dnd.bookkeeping.android.domain.repository.FileRepository
import javax.inject.Inject

class RealFileRepository @Inject constructor(
    private val fileApi: FileApi,
    private val sharedPreferencesManager: SharedPreferencesManager
) : FileRepository {
    override suspend fun getPreSignedUrl(
        fileName: String
    ): Result<PreSignedUrl> {
        return fileApi.getPreSignedUrl(fileName).toDomain()
    }

    override suspend fun upload(
        preSignedUrl: String,
        imageUri: String
    ): Result<Unit> {
        return fileApi.upload(
            preSignedUrl = preSignedUrl,
            imageUri = imageUri
        )
    }
}
