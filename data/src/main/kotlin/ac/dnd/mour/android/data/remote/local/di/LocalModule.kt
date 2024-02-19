package ac.dnd.mour.android.data.remote.local.di

import ac.dnd.mour.android.data.remote.local.SharedPreferencesManager
import ac.dnd.mour.android.data.remote.local.database.MourDatabase
import ac.dnd.mour.android.data.remote.local.database.sample.SampleDao
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
    fun provideMourDatabase(
        @ApplicationContext context: Context
    ): MourDatabase {
        return Room.databaseBuilder(
            context,
            MourDatabase::class.java,
            MourDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideSampleDao(
        mourDatabase: MourDatabase
    ): SampleDao {
        return mourDatabase.sampleDao()
    }
}
