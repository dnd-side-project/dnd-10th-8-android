package ac.dnd.bookkeeping.android.presentation.common.view.gallery

sealed interface GalleryIntent {
    data object OnGrantPermission : GalleryIntent
    data class OnChangeFolder(
        val location: Pair<String, String?>
    ) : GalleryIntent
}
