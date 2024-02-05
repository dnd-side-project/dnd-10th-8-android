package ac.dnd.bookkeeping.android.presentation.ui.main.home.history

import ac.dnd.bookkeeping.android.domain.model.feature.relation.RelationDetail
import ac.dnd.bookkeeping.android.domain.model.feature.relation.RelationDetailGroup
import ac.dnd.bookkeeping.android.presentation.common.theme.Body1
import ac.dnd.bookkeeping.android.presentation.common.theme.Body2
import ac.dnd.bookkeeping.android.presentation.common.theme.Caption1
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray000
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray200
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray600
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray700
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline3
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary4
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary5
import ac.dnd.bookkeeping.android.presentation.common.theme.Shapes
import ac.dnd.bookkeeping.android.presentation.common.theme.Space12
import ac.dnd.bookkeeping.android.presentation.common.theme.Space8
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.DecimalFormat

@Composable
fun HistoryRelationItem(
    relation: RelationDetail,
    onSelectCard: (RelationDetail) -> Unit
) {
    Card(
        shape = Shapes.medium,
        backgroundColor = Gray000,
        modifier = Modifier.clickable {
            onSelectCard(relation)
        }
    ) {
        Column(
            modifier = Modifier.padding(Space12)
        ) {
            Text(
                text = relation.name,
                style = Headline3.merge(
                    color = Gray700,
                    fontWeight = FontWeight.SemiBold
                ),
                lineHeight = 24.sp,
                textAlign = TextAlign.Left
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = relation.group.name,
                style = Caption1.merge(
                    color = Gray600,
                    fontWeight = FontWeight.Normal
                )
            )
            Spacer(modifier = Modifier.height(9.5.dp))
            Divider(
                modifier = Modifier.height(1.dp),
                color = Gray200
            )
            Spacer(modifier = Modifier.height(20.5.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "받음",
                    style = Body1.merge(
                        color = Gray600,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Row(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = DecimalFormat("#,###").format(relation.takeMoney),
                        style = Body1.merge(
                            color = Primary5,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(
                        text = "원",
                        style = Body2.merge(
                            color = Primary4,
                            fontWeight = FontWeight.SemiBold
                        ),
                        textAlign = TextAlign.Left
                    )
                }
            }
            Spacer(modifier = Modifier.height(Space8))
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "보냄",
                    style = Body1.merge(
                        color = Gray600,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Row(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "-${DecimalFormat("#,###").format(relation.giveMoney)}",
                        style = Body1.merge(
                            color = Gray700,
                            fontWeight = FontWeight.SemiBold
                        ),
                    )
                    Text(
                        text = "원",
                        style = Body2.merge(
                            color = Gray700,
                            fontWeight = FontWeight.SemiBold
                        ),
                        textAlign = TextAlign.Left
                    )
                }
            }
            Spacer(modifier = Modifier.height(Space8))
        }
    }
}

@Preview
@Composable
fun HeartItemPreview() {
    HistoryRelationItem(
        relation = RelationDetail(
            id = 8446,
            name = "Harlan Yang",
            group = RelationDetailGroup(
                id = 2337,
                name = "Angelia McBride",
            ),
            giveMoney = 8327,
            takeMoney = 4954
        ),
        onSelectCard = {}
    )
}
