package ac.dnd.bookkeeping.android.data.repository.bookkeeping

import ac.dnd.bookkeeping.android.domain.model.bookkeeping.BookkeepingInformation
import ac.dnd.bookkeeping.android.domain.repository.BookkeepingRepository
import javax.inject.Inject

class MockBookkeepingRepository @Inject constructor() : BookkeepingRepository {
    override suspend fun getBookkeepingInformation(
        apiKey: String,
        title: String,
        artist: String
    ): Result<BookkeepingInformation> {
        return Result.success(BookkeepingInformation())
    }
}
