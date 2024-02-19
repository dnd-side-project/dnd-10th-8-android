package ac.dnd.mour.android.domain.model.error

class UndefinedKeyException(
    override val message: String
) : Exception(message)
