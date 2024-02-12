package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.unrecorded

import ac.dnd.bookkeeping.android.domain.model.feature.schedule.UnrecordedSchedule
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.UnrecordedScheduleRelation
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.UnrecordedScheduleRelationGroup
import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

fun NavGraphBuilder.historyUnrecordedDestination(
    appState: ApplicationState
) {
    composable(
        route = HistoryUnrecordedConstant.ROUTE
    ) {
        //TODO -> List<UnrecordedSchedule>
        val sampleUnrecordedList = listOf(
            UnrecordedSchedule(
                0,
                UnrecordedScheduleRelation(
                    0,
                    "친구",
                    group = UnrecordedScheduleRelationGroup(
                        0,
                        "그룹"
                    )
                ),
                day = LocalDate(2024, 1, 1),
                event = "결혼",
                time = LocalTime(12, 12),
                link = "",
                location = ""
            ),
            UnrecordedSchedule(
                1,
                UnrecordedScheduleRelation(
                    0,
                    "가족",
                    group = UnrecordedScheduleRelationGroup(
                        0,
                        "그룹"
                    )
                ),
                day = LocalDate(2024, 2, 2),
                event = "돌잔치",
                time = LocalTime(12, 12),
                link = "",
                location = ""
            ),
            UnrecordedSchedule(
                2,
                UnrecordedScheduleRelation(
                    0,
                    "친구",
                    group = UnrecordedScheduleRelationGroup(
                        0,
                        "그룹"
                    )
                ),
                day = LocalDate(2024, 3, 3),
                event = "출산",
                time = LocalTime(12, 12),
                link = "",
                location = ""
            ),
            UnrecordedSchedule(
                3,
                UnrecordedScheduleRelation(
                    0,
                    "친구",
                    group = UnrecordedScheduleRelationGroup(
                        0,
                        "그룹"
                    )
                ),
                day = LocalDate(2024, 4, 4),
                event = "생일",
                time = LocalTime(12, 12),
                link = "",
                location = ""
            ),
            UnrecordedSchedule(
                4,
                UnrecordedScheduleRelation(
                    0,
                    "지인",
                    group = UnrecordedScheduleRelationGroup(
                        0,
                        "그룹"
                    )
                ),
                day = LocalDate(2024, 5, 5),
                event = "결혼",
                time = LocalTime(12, 12),
                link = "",
                location = ""
            ),
            UnrecordedSchedule(
                5,
                UnrecordedScheduleRelation(
                    0,
                    "가족",
                    group = UnrecordedScheduleRelationGroup(
                        0,
                        "그룹"
                    )
                ),
                day = LocalDate(2024, 6, 6),
                event = "기타",
                time = LocalTime(12, 12),
                link = "",
                location = ""
            )
        )
        val viewModel: HistoryUnrecordedViewModel = hiltViewModel()
        viewModel.initUnRecordedList(sampleUnrecordedList)

        val model: HistoryUnrecordedModel = let {
            val state by viewModel.state.collectAsStateWithLifecycle()
            val unrecordedList by viewModel.unrecordedList.collectAsStateWithLifecycle()

            HistoryUnrecordedModel(
                state = state,
                unrecordedList = unrecordedList,
            )
        }

        ErrorObserver(viewModel)

        HistoryUnrecordedScreen(
            appState = appState,
            model = model,
            event = viewModel.event,
            intent = viewModel::onIntent,
            handler = viewModel.handler
        )
    }
}
