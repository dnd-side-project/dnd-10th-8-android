package ac.dnd.bookkeeping.android.domain.model.error

class InvalidStandardResponseException(
    override val message: String
) : Exception(message)
