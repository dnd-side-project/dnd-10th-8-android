package ac.dnd.mour.android.data.remote.network.di

import ac.dnd.mour.android.data.remote.local.SharedPreferencesManager
import ac.dnd.mour.android.data.remote.network.environment.BaseUrlProvider
import ac.dnd.mour.android.data.remote.network.environment.ErrorMessageMapper
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkEnvironmentModule {

    @Provides
    @Singleton
    fun provideBaseUrlProvider(
        sharedPreferencesManager: SharedPreferencesManager
    ): BaseUrlProvider {
        return BaseUrlProvider(sharedPreferencesManager)
    }

    @Provides
    @Singleton
    fun provideErrorMessageMapper(
        @ApplicationContext context: Context
    ): ErrorMessageMapper {
        return ErrorMessageMapper(context)
    }
}
