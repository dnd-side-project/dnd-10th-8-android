package ac.dnd.mour.android.presentation.ui.main.home.common.relation

sealed interface RelationEvent {
    sealed interface AddRelation : RelationEvent {
        data class Success(
            val relationId : Long,
            val name : String
        ) : AddRelation
    }

    sealed interface EditRelation : RelationEvent {
        data object Success : EditRelation
    }

    sealed interface DeleteRelation : RelationEvent {
        data object Success : DeleteRelation
    }

    sealed interface LoadKakaoFriend : RelationEvent {
        data class Success(
            val name: String,
            val imageUrl: String
        ) : LoadKakaoFriend
    }
}
