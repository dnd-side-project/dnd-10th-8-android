package ac.dnd.mour.android.presentation.model.relation

import ac.dnd.mour.android.domain.model.feature.relation.RelationDetailGroup
import ac.dnd.mour.android.domain.model.feature.relation.RelationDetailWithUserInfo
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RelationDetailWithUserInfoModel(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val memo: String,
    val group: RelationDetailGroupModel,
    val giveMoney: Long,
    val takeMoney: Long
) : Parcelable

fun RelationDetailWithUserInfo.toUiModel(): RelationDetailWithUserInfoModel {
    return RelationDetailWithUserInfoModel(
        id = id,
        name = name,
        imageUrl = imageUrl,
        memo = memo,
        group = group.toUiModel(),
        giveMoney = giveMoney,
        takeMoney = takeMoney
    )
}

@Parcelize
data class RelationDetailGroupModel(
    val id: Long,
    val name: String
) : Parcelable

fun RelationDetailGroup.toUiModel(): RelationDetailGroupModel {
    return RelationDetailGroupModel(
        id = id,
        name = name
    )
}
