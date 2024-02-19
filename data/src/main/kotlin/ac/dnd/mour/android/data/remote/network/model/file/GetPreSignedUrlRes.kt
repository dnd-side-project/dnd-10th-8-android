package ac.dnd.mour.android.data.remote.network.model.file

import ac.dnd.mour.android.data.remote.mapper.DataMapper
import ac.dnd.mour.android.domain.model.file.PreSignedUrl
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
