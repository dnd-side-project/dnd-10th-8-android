package ac.dnd.bookkeeping.android.data.remote.local.di

import ac.dnd.bookkeeping.android.data.remote.local.SharedPreferencesManager
import ac.dnd.bookkeeping.android.data.remote.local.database.BookKeepingDatabase
import ac.dnd.bookkeeping.android.data.remote.local.database.sample.SampleDao
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object LocalModule {

    @Provides
    @Singleton
    fun provideSharedPreferencesManager(
        @ApplicationContext context: Context
    ): SharedPreferencesManager {
        return SharedPreferencesManager(context)
    }

    @Provides
    @Singleton
    fun provideBookKeepingDatabase(
        @ApplicationContext context: Context
    ): BookKeepingDatabase {
        return Room.databaseBuilder(
            context,
            BookKeepingDatabase::class.java,
            BookKeepingDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideSampleDao(
        bookKeepingDatabase: BookKeepingDatabase
    ): SampleDao {
        return bookKeepingDatabase.sampleDao()
    }
}
