package ac.dnd.bookkeeping.android.data.remote.mapper

interface DataMapper<D> {
    fun toDomain(): D
}
