package ac.dnd.mour.android.domain.model.error

class InvalidStandardResponseException(
    override val message: String
) : Exception(message)
