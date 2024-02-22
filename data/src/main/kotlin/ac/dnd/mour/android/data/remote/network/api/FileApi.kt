package ac.dnd.mour.android.data.remote.network.api

import ac.dnd.mour.android.data.remote.network.di.AuthHttpClient
import ac.dnd.mour.android.data.remote.network.di.NoAuthHttpClient
import ac.dnd.mour.android.data.remote.network.environment.BaseUrlProvider
import ac.dnd.mour.android.data.remote.network.environment.ErrorMessageMapper
import ac.dnd.mour.android.data.remote.network.model.file.GetPreSignedUrlRes
import ac.dnd.mour.android.data.remote.network.util.convert
import ac.dnd.mour.android.data.remote.network.util.parameterFiltered
import android.net.Uri
import android.webkit.MimeTypeMap
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import java.io.File
import javax.inject.Inject

class FileApi @Inject constructor(
    @NoAuthHttpClient private val noAuthClient: HttpClient,
    @AuthHttpClient private val client: HttpClient,
    private val baseUrlProvider: BaseUrlProvider,
    private val errorMessageMapper: ErrorMessageMapper
) {
    private val baseUrl: String
        get() = baseUrlProvider.get()

    suspend fun getPreSignedUrl(
        fileName: String
    ): Result<GetPreSignedUrlRes> {
        return client.get("$baseUrl/api/v1/files/presigned/image") {
            parameterFiltered("fileName", fileName)
        }.convert(errorMessageMapper::map)
    }

    suspend fun upload(
        preSignedUrl: String,
        imageUri: String,
        fileName: String? = null
    ): Result<Unit> {
        val image = Uri.parse(imageUri)?.path ?: let {
            return Result.failure(IllegalArgumentException("Invalid imageUri"))
        }
        val file = File(image)
        val name = fileName ?: file.name
        val contentType = getContentType(file.path)

        return noAuthClient.submitFormWithBinaryData(
            url = preSignedUrl,
            formData = formData {
                append(
                    "image",
                    file.readBytes(),
                    Headers.build {
                        contentType?.let { append(HttpHeaders.ContentType, it) }
                        append(HttpHeaders.ContentDisposition, "filename=$name")
                    }
                )
            }
        ).convert(errorMessageMapper::map)
    }

    private fun getContentType(
        url: String
    ): String? {
        return MimeTypeMap.getFileExtensionFromUrl(url)?.let { fileExtension ->
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension)
        }
    }
}
