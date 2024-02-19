package ac.dnd.mour.android.presentation.ui.main.registration.main.component

import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.theme.Body1
import ac.dnd.mour.android.presentation.common.theme.Negative
import ac.dnd.mour.android.presentation.common.theme.Space4
import ac.dnd.mour.android.presentation.ui.main.registration.main.type.RegistrationMainNamingErrorType
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ErrorUserNamingComponent(namingType: RegistrationMainNamingErrorType) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        when (namingType) {
            RegistrationMainNamingErrorType.Init -> Spacer(Modifier.height(21.dp))
            is RegistrationMainNamingErrorType.InValid.Duplication -> {
                Image(
                    painter = painterResource(R.drawable.ic_alert_triangle),
                    contentDescription = null
                )
                Spacer(Modifier.width(Space4))
                Text(
                    text = "중복 된 닉네임이 있습니다.",
                    style = Body1.merge(color = Negative)
                )
            }

            is RegistrationMainNamingErrorType.InValid.NumberOutOfRange -> {
                Image(
                    painter = painterResource(R.drawable.ic_alert_triangle),
                    contentDescription = null
                )
                Spacer(Modifier.width(Space4))
                Text(
                    text = "15자 이내로 입력 해주세요.",
                    style = Body1.merge(color = Negative)
                )
            }
        }
    }
}
