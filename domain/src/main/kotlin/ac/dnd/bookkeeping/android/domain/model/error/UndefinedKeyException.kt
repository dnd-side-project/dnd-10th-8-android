package ac.dnd.bookkeeping.android.domain.model.error

class UndefinedKeyException(
    override val message: String
) : Exception(message)
