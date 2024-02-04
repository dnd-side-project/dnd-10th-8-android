package ac.dnd.bookkeeping.android.presentation.common.view.calendar

import java.util.Calendar

class CalendarConfig {
    private val calendar: Calendar = Calendar.getInstance()
    private var maxDate = 0
    private var todayDay = calendar.get(Calendar.DAY_OF_MONTH)

    fun getCurrentCalendarDate(
        year: Int,
        month: Int
    ): List<CalendarDay> {
        calendar.apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month - 1)
            maxDate = getActualMaximum(Calendar.DATE)
        }
        val dataSet = ArrayList<CalendarDay>()
        makeMonthDate(dataSet)
        return dataSet
    }

    fun getCalendarYear() = calendar.get(Calendar.YEAR)
    fun getCalendarMonth() = calendar.get(Calendar.MONTH)+1
    fun getCalendarDay() = todayDay
    fun getDayOfWeek() = DAY_OF_WEEK

    private fun makeMonthDate(dataSet: ArrayList<CalendarDay>) {
        calendar.set(Calendar.DATE, 1)
        todayDay = 0
        val prevTail = calendar.get(Calendar.DAY_OF_WEEK) - 1
        makeBeforeDays(prevTail, dataSet)
        makeCurrentDays(dataSet)
    }

    private fun makeBeforeDays(
        prevTail: Int,
        dataSet: ArrayList<CalendarDay>,
    ) {
        var maxOffsetDate = maxDate - prevTail
        for (i in 1..prevTail) {
            dataSet.add(
                CalendarDay(
                    day = ++maxOffsetDate,
                    dayType = CalendarDayType.OTHER_MONTH
                )
            )
        }
    }

    private fun makeCurrentDays(dataSet: ArrayList<CalendarDay>) {
        val todayCalendar = Calendar.getInstance()
        val calenderDate = todayCalendar.get(Calendar.DATE)
        val todayDate = todayCalendar.get(Calendar.YEAR) * 12 + todayCalendar.get(Calendar.MONTH)
        val currentDate = calendar.get(Calendar.YEAR) * 12 + calendar.get(Calendar.MONTH)
        var currentDayType =
            if (todayDate < currentDate) CalendarDayType.AFTER_TODAY else CalendarDayType.BEFORE_TODAY

        for (i in 1..maxDate) {
            if (todayDate == currentDate && i == calenderDate) {
                todayDay = i
                dataSet.add(CalendarDay(i, CalendarDayType.TODAY))
                currentDayType = CalendarDayType.AFTER_TODAY
            } else {
                dataSet.add(CalendarDay(i, currentDayType))
            }
        }
    }

    companion object {
        private val DAY_OF_WEEK = listOf("일", "월", "화", "수", "목", "금", "토")
    }
}
