package ac.dnd.bookkeeping.android.domain.usecase.bookkeeping

import ac.dnd.bookkeeping.android.domain.model.bookkeeping.BookkeepingInformation
import ac.dnd.bookkeeping.android.domain.repository.BookkeepingRepository
import javax.inject.Inject

class GetBookkeepingInformationUseCase @Inject constructor(
    private val bookkeepingRepository: BookkeepingRepository
) {
    suspend operator fun invoke(): Result<BookkeepingInformation> {
        return bookkeepingRepository.getBookkeepingInformation(
            apiKey = "",
            title = "",
            artist = ""
        )
    }
}
