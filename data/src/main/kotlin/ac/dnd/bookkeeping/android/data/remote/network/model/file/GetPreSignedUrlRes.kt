package ac.dnd.bookkeeping.android.data.remote.network.model.file

import ac.dnd.bookkeeping.android.data.remote.mapper.DataMapper
import ac.dnd.bookkeeping.android.domain.model.file.PreSignedUrl
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetPreSignedUrlRes(
    @SerialName("preSignedUrl")
    val preSignedUrl: String,
    @SerialName("uploadFileUrl")
    val uploadFileUrl: String
) : DataMapper<PreSignedUrl> {
    override fun toDomain(): PreSignedUrl {
        return PreSignedUrl(
            preSignedUrl = preSignedUrl,
            uploadFileUrl = uploadFileUrl
        )
    }
}
