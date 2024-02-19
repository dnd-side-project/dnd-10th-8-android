package ac.dnd.bookkeeping.android.data.remote.local.database

import ac.dnd.bookkeeping.android.data.remote.local.database.sample.SampleDao
import ac.dnd.bookkeeping.android.data.remote.local.database.sample.SampleEntity
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SampleEntity::class], version = 1)
abstract class BookKeepingDatabase : RoomDatabase() {
    abstract fun sampleDao(): SampleDao

    companion object {
        const val DATABASE_NAME = "bookkeeping"
    }
}
