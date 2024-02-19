package ac.dnd.mour.android.data.remote.mapper

interface DataMapper<D> {
    fun toDomain(): D
}
