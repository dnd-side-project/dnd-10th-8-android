package ac.dnd.mour.android.presentation.ui.main.registration.main.type

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface UserGender : Parcelable {
    @Parcelize
    data object Male : UserGender

    @Parcelize
    data object Female : UserGender
}
