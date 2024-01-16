package ac.dnd.bookkeeping.android.domain.model.error

class InternalServerException(
    override val id: String,
    override val message: String
) : ServerException(id, message)
