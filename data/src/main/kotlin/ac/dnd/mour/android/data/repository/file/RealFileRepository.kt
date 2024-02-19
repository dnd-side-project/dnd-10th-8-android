package ac.dnd.mour.android.data.repository.file

import ac.dnd.mour.android.data.remote.local.SharedPreferencesManager
import ac.dnd.mour.android.data.remote.network.api.FileApi
import ac.dnd.mour.android.data.remote.network.util.toDomain
import ac.dnd.mour.android.domain.model.file.PreSignedUrl
import ac.dnd.mour.android.domain.repository.FileRepository
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
