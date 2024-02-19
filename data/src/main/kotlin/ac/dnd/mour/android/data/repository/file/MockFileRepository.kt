package ac.dnd.mour.android.data.repository.file

import ac.dnd.mour.android.domain.model.file.PreSignedUrl
import ac.dnd.mour.android.domain.repository.FileRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class MockFileRepository @Inject constructor() : FileRepository {
    override suspend fun getPreSignedUrl(
        fileName: String
    ): Result<PreSignedUrl> {
        randomShortDelay()
        return Result.success(
            PreSignedUrl(
                preSignedUrl = "https://example.com",
                uploadFileUrl = "https://example.com"
            )
        )
    }

    override suspend fun upload(
        preSignedUrl: String,
        imageUri: String
    ): Result<Unit> {
        randomLongDelay()
        return Result.success(Unit)
    }

    private suspend fun randomShortDelay() {
        delay(LongRange(100, 500).random())
    }

    private suspend fun randomLongDelay() {
        delay(LongRange(500, 2000).random())
    }
}
