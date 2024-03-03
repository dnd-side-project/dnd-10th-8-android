package ac.dnd.mour.android.presentation.ui.main.home.common.relation.get

sealed interface GetRelationIntent{
    data object OnRefresh : GetRelationIntent
}
