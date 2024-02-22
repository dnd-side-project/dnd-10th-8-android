package ac.dnd.mour.android.data.remote.network.environment

import ac.dnd.mour.android.data.R
import ac.dnd.mour.android.domain.model.error.UndefinedKeyException
import android.content.Context
import timber.log.Timber

class ErrorMessageMapper(
    private val context: Context
) {
    fun map(
        key: String
    ): String {
        val id = when (key) {
            KEY_INTERNAL_SERVER_ERROR,
            NOT_SUPPORTED_URI_ERROR,
            NOT_SUPPORTED_METHOD_ERROR,
            VALIDATION_ERROR,
            UNSUPPORTED_MEDIA_TYPE_ERROR,
            UNEXPECTED_SERVER_ERROR -> R.string.error_internal_server_error

            AUTH_REQUIRED -> R.string.error_auth_required
            INVALID_TOKEN -> R.string.error_invalid_token
            INVALID_PERMISSION -> R.string.error_invalid_permission

            INVALID_FILE_EXTENSION -> R.string.error_invalid_file_extension

            GROUP_NOT_FOUND -> R.string.error_group_not_found
            GROUP_ALREADY_EXISTS -> R.string.error_group_already_exists

            HEART_NOT_FOUND -> R.string.error_heart_not_found
            INVALID_SORT -> R.string.error_invalid_sort
            INVALID_TYPE -> R.string.error_invalid_type

            MEMBER_NOT_FOUND -> R.string.error_member_not_found
            INVALID_EMAIL_PATTERN -> R.string.error_invalid_email_pattern
            INVALID_GENDER -> R.string.error_invalid_gender
            INVALID_NICKNAME_PATTERN -> R.string.error_invalid_nickname_pattern
            DUPLICATE_NICKNAME -> R.string.error_duplicate_nickname
            MEMBER_GROUP_NAME_TOO_LONG -> R.string.error_member_group_name_too_long
            MEMBER_GROUP_ALREADY_EXISTS -> R.string.error_member_group_already_exists
            MEMBER_GROUP_NOT_FOUND -> R.string.error_member_group_not_found

            RELATION_NOT_FOUND -> R.string.error_relation_not_found

            SCHEDULE_NOT_FOUND -> R.string.error_schedule_not_found
            INVALID_REPEAT_TYPE -> R.string.error_invalid_repeat_type
            INVALID_SCHEDULE_DAY -> R.string.error_invalid_schedule_day

            else -> {
                Timber.e(UndefinedKeyException("Undefined error key: $key"))
                R.string.error_unknown
            }
        }

        return context.getString(id)
    }

    companion object {
        const val KEY_INTERNAL_SERVER_ERROR = "SERVER:INTERNAL_SERVER_ERROR"

        const val NOT_SUPPORTED_URI_ERROR = "GLOBAL_001"
        const val NOT_SUPPORTED_METHOD_ERROR = "GLOBAL_002"
        const val VALIDATION_ERROR = "GLOBAL_003"
        const val UNSUPPORTED_MEDIA_TYPE_ERROR = "GLOBAL_004"
        const val UNEXPECTED_SERVER_ERROR = "GLOBAL_005"

        const val AUTH_REQUIRED = "AUTH_001"
        const val INVALID_TOKEN = "AUTH_002"
        const val INVALID_PERMISSION = "AUTH_003"

        const val INVALID_FILE_EXTENSION = "FILE_001"

        const val GROUP_NOT_FOUND = "GROUP_001"
        const val GROUP_ALREADY_EXISTS = "GROUP_002"

        const val HEART_NOT_FOUND = "HEART_001"
        const val INVALID_SORT = "HEART_002"
        const val INVALID_TYPE = "HEART_003"

        const val MEMBER_NOT_FOUND = "MEMBER_001"
        const val INVALID_EMAIL_PATTERN = "MEMBER_002"
        const val INVALID_GENDER = "MEMBER_003"
        const val INVALID_NICKNAME_PATTERN = "MEMBER_004"
        const val DUPLICATE_NICKNAME = "MEMBER_005"
        const val MEMBER_GROUP_NAME_TOO_LONG = "MEMBER_OO6"
        const val MEMBER_GROUP_ALREADY_EXISTS = "MEMBER_007"
        const val MEMBER_GROUP_NOT_FOUND = "MEMBER_008"

        const val RELATION_NOT_FOUND = "RELATION_001"

        const val SCHEDULE_NOT_FOUND = "SCHEDULE_001"
        const val INVALID_REPEAT_TYPE = "SCHEDULE_002"
        const val INVALID_SCHEDULE_DAY = "SCHEDULE_003"
    }
}
