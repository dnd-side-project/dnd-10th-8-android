package ac.dnd.mour.android.data.remote.network.di

import ac.dnd.mour.android.domain.repository.AuthenticationRepository
import android.content.Context
import android.content.pm.ApplicationInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.resources.Resources
import io.ktor.serialization.kotlinx.json.json
import java.util.Optional
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.Interceptor

@Module
@InstallIn(SingletonComponent::class)
internal object KtorModule {

    private val jsonConfiguration = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Provides
    @Singleton
    @NoAuthHttpClient
    fun provideNoAuthHttpClient(
        @ApplicationContext context: Context,
        @DebugInterceptor debugInterceptor: Optional<Interceptor>
    ): HttpClient {
        return HttpClient(OkHttp) {
            // default validation to throw exceptions for non-2xx responses
            expectSuccess = true

            engine {
                if (debugInterceptor.isPresent) {
                    addInterceptor(debugInterceptor.get())
                }
            }

            install(ContentNegotiation) {
                json(jsonConfiguration)
            }
            install(Resources)
        }
    }

    @Provides
    @Singleton
    @AuthHttpClient
    fun provideAuthHttpClient(
        @ApplicationContext context: Context,
        @DebugInterceptor debugInterceptor: Optional<Interceptor>,
        authenticationRepository: AuthenticationRepository
    ): HttpClient {
        val isDebug: Boolean =
            (0 != context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE)

        return HttpClient(OkHttp) {
            // default validation to throw exceptions for non-2xx responses
            // TODO
            expectSuccess = false

            engine {
                if (debugInterceptor.isPresent) {
                    addInterceptor(debugInterceptor.get())
                }
            }

            install(ContentNegotiation) {
                json(jsonConfiguration)
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        val accessToken = authenticationRepository.accessToken
                        val refreshToken = authenticationRepository.refreshToken
                        if (accessToken.isEmpty() || refreshToken.isEmpty()) {
                            return@loadTokens null
                        }

                        BearerTokens(
                            accessToken = accessToken,
                            refreshToken = refreshToken
                        )
                    }

                    refreshTokens {
                        val refreshToken = authenticationRepository.refreshToken
                        if (refreshToken.isEmpty()) {
                            return@refreshTokens null
                        }

                        authenticationRepository.refreshToken(
                            refreshToken
                        ).getOrNull()?.let { token ->
                            BearerTokens(
                                accessToken = token.accessToken,
                                refreshToken = token.refreshToken
                            )
                        }
                    }
                }
            }
        }
    }
}

@Qualifier
annotation class NoAuthHttpClient

@Qualifier
annotation class AuthHttpClient
