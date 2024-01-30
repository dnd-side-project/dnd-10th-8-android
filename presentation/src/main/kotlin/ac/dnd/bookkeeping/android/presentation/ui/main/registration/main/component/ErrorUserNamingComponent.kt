package ac.dnd.bookkeeping.android.presentation.ui.main.registration.main.component

import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Body1
import ac.dnd.bookkeeping.android.presentation.common.theme.Negative
import ac.dnd.bookkeeping.android.presentation.common.theme.Space4
import ac.dnd.bookkeeping.android.presentation.ui.main.registration.main.RegistrationMainErrorType
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ErrorUserNamingComponent(namingErrorType: RegistrationMainErrorType) {
    Row {
        when (namingErrorType) {
            RegistrationMainErrorType.Init -> Spacer(Modifier.height(21.dp))
            is RegistrationMainErrorType.InValid.Duplication -> {
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

            is RegistrationMainErrorType.InValid.NumberOutOfRange -> {
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
