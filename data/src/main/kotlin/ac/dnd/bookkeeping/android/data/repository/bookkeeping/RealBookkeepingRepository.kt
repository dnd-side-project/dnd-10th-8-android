package ac.dnd.bookkeeping.android.data.repository.bookkeeping

import ac.dnd.bookkeeping.android.data.remote.network.api.BookkeepingApi
import ac.dnd.bookkeeping.android.data.remote.network.util.toDomain
import ac.dnd.bookkeeping.android.domain.model.bookkeeping.BookkeepingInformation
import ac.dnd.bookkeeping.android.domain.repository.BookkeepingRepository
import javax.inject.Inject

class RealBookkeepingRepository @Inject constructor(
    private val bookkeepingApi: BookkeepingApi
) : BookkeepingRepository {
    override suspend fun getBookkeepingInformation(
        apiKey: String,
        title: String,
        artist: String
    ): Result<BookkeepingInformation> {
        return bookkeepingApi.getLyrics(
            apiKey = apiKey,
            title = title,
            artist = artist
        ).toDomain()
    }
}
