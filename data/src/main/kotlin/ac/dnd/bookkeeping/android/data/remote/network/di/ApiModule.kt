package ac.dnd.bookkeeping.android.data.remote.network.di

import ac.dnd.bookkeeping.android.data.remote.network.environment.ErrorMessageMapper
import ac.dnd.bookkeeping.android.data.remote.network.environment.BaseUrlProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {

    @Provides
    @Singleton
    fun provideBookkeepingApi(
        client: HttpClient,
        baseUrlProvider: BaseUrlProvider,
        errorMessageMapper: ErrorMessageMapper
    ): BookkeepingApi {
        return BookkeepingApi(
            client,
            baseUrlProvider,
            errorMessageMapper
        )
    }
}
