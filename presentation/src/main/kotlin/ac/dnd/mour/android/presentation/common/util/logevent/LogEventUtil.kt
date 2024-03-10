package ac.dnd.mour.android.presentation.common.util.logevent

import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.ktx.Firebase

fun viewLogEvent(
    eventName: String,
    screenName: String
) {
    Firebase.analytics.logEvent(eventName) {
        param(screenName, screenName)
    }
}

object LogEventUtil {
    const val VIEW_LOG_IN = "view_log_in"
    const val CLICK_KAKAO_LOG_IN = "click_kakao_log_in"

    const val VIEW_ONBOARDING = "View_onboarding"
    const val CLICK_NEXT_ONBOARDING = "click_next_onboarding"
    const val VIEW_ONBOARDING_SECOND = "View_onboarding_second"
    const val CLICK_NEXT_ONBOARDING_SECOND = "click_next_onboarding_second"
    const val VIEW_ONBOARDING_THIRD = "View_onboarding_third"
    const val CLICK_NEXT_ONBOARDING_THIRD = "click_next_onboarding_third"

    const val VIEW_REQUIRED_INFO = "View_Required_info"
    const val CLICK_START_REQUIRED_INFO = "click_start_Required_info"

    const val CLICK_PLUSBTN_MAIN = "click_Plusbtn_main"
    const val VIEW_MAIN = "View_main"
    const val CLICK_PLUSFLOATING_MAIN = "click_Plusfloating_main"

    const val VIEW_FAB_BUTTON_MAIN = "View_FAB_button_main"
    const val CLICK_MONEY_MAIN = "click_money_main"
    const val CLICK_RELATION_MAIN = "click_relation_main"
    const val CLICK_CLOSE_MAIN = "click_close_main"

    const val VIEW_RELATION = "View_relation"
    const val CLICK_DIRECT_INPUT_RELATION = "click_Direct_input_relation"
    const val CLICK_MONEY_RELATION = "click_money_relation"
    const val CLICK_KAKAO_RELATION = "click_kakao_relation"
    const val CLICK_EDIT_RELATION = "click_edit_relation"
    const val CLICK_SAVE_RELATION = "click_save_relation"

    const val VIEW_EDIT_GROUP = "View_Edit_group"
    const val CLICK_SAVE_GROUP_EDIT_GROUP = "click_save_group_edit_group"

    const val VIEW_ADD_NEW_GROUP = "View_Add_new_group"
    const val CLICK_REGISTRATION_ADD_NEW_GROUP = "click_registration_Add_new_group"

    const val VIEW_MONEY_TAKE = "View_money_take"
    const val CLICK_NEXT_SAVE_TAKE = "click_next_save_take"
    const val CLICK_SAVE_TAKE = "click_save_take"

    const val VIEW_MONEY_GIVE = "View_money_give"
    const val CLICK_NEXT_SAVE_GIVE = "click_next_save_give"
    const val CLICK_SAVE_GIVE = "click_save_give"

    const val VIEW_SELECT_NAME = "View_select_name"
    const val CLICK_SAVE_RELATION_SELECT_NAME = "click_save_relation_select_name"
    const val CLICK_SELECION_NAME = "click_selecion_name"
}
