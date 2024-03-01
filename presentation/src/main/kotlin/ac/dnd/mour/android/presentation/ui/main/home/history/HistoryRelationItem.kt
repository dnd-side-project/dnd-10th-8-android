package ac.dnd.mour.android.presentation.ui.main.home.history

import ac.dnd.mour.android.domain.model.feature.relation.RelationDetail
import ac.dnd.mour.android.domain.model.feature.relation.RelationDetailGroup
import ac.dnd.mour.android.presentation.common.theme.Body1
import ac.dnd.mour.android.presentation.common.theme.Body2
import ac.dnd.mour.android.presentation.common.theme.Gray000
import ac.dnd.mour.android.presentation.common.theme.Gray600
import ac.dnd.mour.android.presentation.common.theme.Gray800
import ac.dnd.mour.android.presentation.common.theme.Headline3
import ac.dnd.mour.android.presentation.common.theme.Primary4
import ac.dnd.mour.android.presentation.common.theme.Primary5
import ac.dnd.mour.android.presentation.common.theme.Shapes
import ac.dnd.mour.android.presentation.model.history.HistoryViewType
import ac.dnd.mour.android.presentation.model.relation.DefaultGroupType
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.DecimalFormat

@Composable
fun HistoryRelationItem(
    viewType: HistoryViewType,
    relation: RelationDetail,
    onSelectCard: (RelationDetail) -> Unit
) {
    Card(
        shape = Shapes.large,
        backgroundColor = Gray000,
        modifier = Modifier
            .clip(Shapes.large)
            .clickable {
                onSelectCard(relation)
            }
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(DefaultGroupType.getGroupResource(relation.group.name)),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = relation.name,
                        fontWeight = FontWeight.SemiBold,
                        style = Headline3.merge(color = Gray800),
                        lineHeight = 24.sp,
                        textAlign = TextAlign.Left,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(1.dp))
                    Text(
                        text = relation.group.name,
                        fontWeight = FontWeight.Medium,
                        style = Body1.merge(color = Gray600),
                        lineHeight = 21.sp,
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            if (viewType != HistoryViewType.GIVE) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "받음",
                        fontWeight = FontWeight.Medium,
                        style = Body1.merge(color = Gray600),
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
                Spacer(modifier = Modifier.height(4.dp))
            }
            if (viewType != HistoryViewType.TAKE) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "보냄",
                        fontWeight = FontWeight.Medium,
                        style = Body1.merge(color = Gray600),
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                    Row(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "-${DecimalFormat("#,###").format(relation.giveMoney)}",
                            style = Body1.merge(
                                color = Color(0xFF1187D8),
                                fontWeight = FontWeight.SemiBold
                            ),
                        )
                        Text(
                            text = "원",
                            style = Body2.merge(
                                color = Color(0xFF1187D8),
                                fontWeight = FontWeight.SemiBold
                            ),
                            textAlign = TextAlign.Left
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Preview
@Composable
fun HeartItemPreview() {
    Column {
        HistoryRelationItem(
            viewType = HistoryViewType.TOTAL,
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
        HistoryRelationItem(
            viewType = HistoryViewType.TAKE,
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
        HistoryRelationItem(
            viewType = HistoryViewType.GIVE,
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
}
