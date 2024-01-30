package ac.dnd.bookkeeping.android.presentation.ui.main.registration.naming.component

import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Body1
import ac.dnd.bookkeeping.android.presentation.common.theme.Negative
import ac.dnd.bookkeeping.android.presentation.common.theme.Space4
import ac.dnd.bookkeeping.android.presentation.ui.main.registration.naming.RegistrationNamingErrorType
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
fun ErrorUserNamingComponent(namingErrorType: RegistrationNamingErrorType) {
    Row {
        when (namingErrorType) {
            RegistrationNamingErrorType.Init -> Spacer(Modifier.height(21.dp))
            is RegistrationNamingErrorType.InValid.Duplication -> {
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

            is RegistrationNamingErrorType.InValid.NumberOutOfRange -> {
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
