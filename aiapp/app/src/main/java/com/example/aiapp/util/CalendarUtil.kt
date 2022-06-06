package com.example.aiapp.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.provider.CalendarContract
import android.text.TextUtils
import java.util.*

/**
 * 工具类：
 * 通过日历添加事件提醒的方式实现秒杀、抢购等提醒功能。
 * 要求内部实现：
 * 1，新增提醒是否是重复提醒，是则添加到相关事件下；否则添加到新事件
 * 2，过期事件、提醒的清理能力
 * https://pixso.cn/app/editor/dsV2VZJOD25D3iJYE4JHVQ
 * 日历相关的资料：https://developer.android.com/guide/topics/providers/calendar-provider.html?hl=zh-cn#calendar
 */
@SuppressLint("Range")
object CalendarUtil {
    private var calanderURL: String? = null
    private var calanderEventURL: String? = null
    private var calanderRemiderURL: String? = null
    private const val CALENDARS_NAME = "XXXX"
    private const val CALENDARS_ACCOUNT_NAME = "XXXX"
    private const val CALENDARS_ACCOUNT_TYPE = "XXXXX"
    private const val CALENDARS_DISPLAY_NAME = "XXXXX"

    /*
     * 初始化uri
     */
    init {
        calanderURL = "content://com.android.calendar/calendars"
        calanderEventURL = "content://com.android.calendar/events"
        calanderRemiderURL = "content://com.android.calendar/reminders"
    }

    /**
     * 获取日历ID
     *
     * @param context
     * @return 日历ID
     */
    private fun checkAndAddCalendarAccounts(context: Context): Int {
        val oldId = checkCalendarAccounts(context)
        return if (oldId >= 0) {
            oldId
        } else {
            val addId = addCalendarAccount(context)
            if (addId >= 0) {
                checkCalendarAccounts(context)
            } else {
                -1
            }
        }
    }

    /**
     * 检查是否存在日历账户
     *
     * @param context
     * @return
     */
    private fun checkCalendarAccounts(context: Context): Int {
        val userCursor = context.contentResolver.query(
            Uri.parse(calanderURL),
            null,
            null,
            null,
            CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL + " ASC "
        )
        return try {
            if (userCursor == null) //查询返回空值
                return -1
            val count = userCursor.count
            if (count > 0) { //存在现有账户，取第一个账户的id返回
                userCursor.moveToLast()
                userCursor.getInt(userCursor.getColumnIndex(CalendarContract.Calendars._ID))
            } else {
                -1
            }
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        } finally {
            userCursor?.close()
        }
    }

    /**
     * 添加一个日历账户
     *
     * @param context
     * @return
     */
    private fun addCalendarAccount(context: Context): Long {
        val timeZone = TimeZone.getDefault()
        val value = ContentValues()
        value.put(CalendarContract.Calendars.NAME, CALENDARS_NAME)
        value.put(CalendarContract.Calendars.ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME)
        value.put(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE)
        value.put(
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
            CALENDARS_DISPLAY_NAME
        )
        value.put(CalendarContract.Calendars.VISIBLE, 1)
        value.put(CalendarContract.Calendars.CALENDAR_COLOR, Color.BLUE)
        value.put(
            CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL,
            CalendarContract.Calendars.CAL_ACCESS_OWNER
        )
        value.put(CalendarContract.Calendars.SYNC_EVENTS, 1)
        value.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, timeZone.id)
        value.put(CalendarContract.Calendars.OWNER_ACCOUNT, CALENDARS_ACCOUNT_NAME)
        value.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 0)
        var calendarUri = Uri.parse(calanderURL)
        calendarUri = calendarUri.buildUpon()
            .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
            .appendQueryParameter(
                CalendarContract.Calendars.ACCOUNT_NAME,
                CALENDARS_ACCOUNT_NAME
            )
            .appendQueryParameter(
                CalendarContract.Calendars.ACCOUNT_TYPE,
                CALENDARS_ACCOUNT_TYPE
            )
            .build()
        val result = context.contentResolver.insert(calendarUri, value)
        return if (result == null) -1 else ContentUris.parseId(result)
    }

    /**
     * 向日历中添加一个事件
     *
     * @param context
     * @param calendar_id （必须参数）
     * @param title
     * @param description
     * @param begintime   事件开始时间，以从公元纪年开始计算的协调世界时毫秒数表示。 （必须参数）
     * @param endtime     事件结束时间，以从公元纪年开始计算的协调世界时毫秒数表示。（非重复事件：必须参数）
     * @return
     */
    private fun insertCalendarEvent(
        context: Context,
        calendar_id: Long,
        title: String,
        description: String,
        begintime: Long,
        endtime: Long
    ): Uri? {
        val event = ContentValues()
        event.put("title", title)
        event.put("description", description)
        // 插入账户的id
        event.put("calendar_id", calendar_id)
        event.put(CalendarContract.Events.DTSTART, begintime) //必须有
        event.put(CalendarContract.Events.DTEND, endtime) //非重复事件：必须有
        event.put(CalendarContract.Events.HAS_ALARM, 1) //设置有闹钟提醒
        event.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id) //这个是时区，必须有，
        //添加事件
        return context.contentResolver.insert(Uri.parse(calanderEventURL), event)
    }

    /**
     * 查询日历事件
     *
     * @param context
     * @param title   事件标题
     * @return 事件id, 查询不到则返回""
     */
    @SuppressLint("Recycle")
    private fun queryCalendarEvent(
        context: Context,
        calendar_id: Long,
        title: String,
        description: String,
        start_time: Long,
        end_time: Long
    ): String {
        // 根据日期范围构造查询
        val builder = CalendarContract.Instances.CONTENT_URI.buildUpon()
        ContentUris.appendId(builder, start_time)
        ContentUris.appendId(builder, end_time)
        val cursor = context.contentResolver.query(builder.build(), null, null, null, null)
        var tmp_title: String?
        var tmp_desc: String?
        var temp_calendar_id: Long
        if (cursor!!.moveToFirst()) {
            do {
                tmp_title = cursor.getString(cursor.getColumnIndex("title"))
                tmp_desc = cursor.getString(cursor.getColumnIndex("description"))
                temp_calendar_id = cursor.getLong(cursor.getColumnIndex("calendar_id"))
                val dtstart = cursor.getLong(cursor.getColumnIndex("dtstart"))
                if (TextUtils.equals(title, tmp_title) && TextUtils.equals(
                        description,
                        tmp_desc
                    ) && calendar_id == temp_calendar_id && dtstart == start_time
                ) {
                    return cursor.getString(cursor.getColumnIndex("event_id"))
                }
            } while (cursor.moveToNext())
        }
        return ""
    }

    /**
     * 添加日历提醒：标题、描述、开始时间共同标定一个单独的提醒事件
     *
     * @param context
     * @param title          日历提醒的标题,不允许为空
     * @param description    日历的描述（备注）信息
     * @param begintime      事件开始时间，以从公元纪年开始计算的协调世界时毫秒数表示。
     * @param endtime        事件结束时间，以从公元纪年开始计算的协调世界时毫秒数表示。
     * @param remind_minutes 提前remind_minutes分钟发出提醒
     * @param callback       添加提醒是否成功结果监听
     */
    fun addCalendarEventRemind(
        activity: Activity,
        title: String,
        description: String,
        begintime: Long,
        endtime: Long,
        remind_minutes: Int,
        callback: onCalendarRemindListener?
    ) {
        if (!PermissionUtil.hasPermissions(
                activity,
                android.Manifest.permission.READ_CALENDAR,
                android.Manifest.permission.WRITE_CALENDAR
            )
        ) {
            PermissionUtil.requestNeededPermissions(
                activity, arrayOf(
                    android.Manifest.permission.READ_CALENDAR,
                    android.Manifest.permission.WRITE_CALENDAR
                )
            )
            return
        }

        val calendar_id = checkAndAddCalendarAccounts(activity).toLong()
        if (calendar_id < 0) {
            // 获取日历失败直接返回
            callback?.onFailed(onCalendarRemindListener.Status._CALENDAR_ERROR)
            return
        }
        //根据标题、描述、开始时间查看提醒事件是否已经存在
        var event_id =
            queryCalendarEvent(activity, calendar_id, title, description, begintime, endtime)
        //如果提醒事件不存在，则新建事件
        if (TextUtils.isEmpty(event_id)) {
            val newEvent =
                insertCalendarEvent(activity, calendar_id, title, description, begintime, endtime)
            if (newEvent == null) {
                // 添加日历事件失败直接返回
                callback?.onFailed(onCalendarRemindListener.Status._EVENT_ERROR)
                return
            }
            event_id = ContentUris.parseId(newEvent).toString() + ""
        }
        //为事件设定提醒
        val values = ContentValues()
        values.put(CalendarContract.Reminders.EVENT_ID, event_id)
        // 提前remind_minutes分钟有提醒
        values.put(CalendarContract.Reminders.MINUTES, remind_minutes)
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT)
        val uri = activity.contentResolver.insert(Uri.parse(calanderRemiderURL), values)
        if (uri == null) {
            // 添加提醒失败直接返回
            callback?.onFailed(onCalendarRemindListener.Status._REMIND_ERROR)
            return
        }

        //添加提醒成功
        callback?.onSuccess()
    }

    /**
     * 删除日历提醒事件：根据标题、描述和开始时间来定位日历事件
     *
     * @param context
     * @param title       提醒的标题
     * @param description 提醒的描述：deeplink URI
     * @param startTime   事件的开始时间
     * @param callback    删除成功与否的监听回调
     */
    fun deleteCalendarEventRemind(
        context: Context,
        title: String,
        description: String,
        startTime: Long,
        callback: onCalendarRemindListener?
    ) {
        context.contentResolver.query(Uri.parse(calanderEventURL), null, null, null, null)
            .use { eventCursor ->
                if (eventCursor == null) //查询返回空值
                    return
                if (eventCursor.count > 0) {
                    //遍历所有事件，找到title、description、startTime跟需要查询的title、descriptio、dtstart一样的项
                    eventCursor.moveToFirst()
                    while (!eventCursor.isAfterLast) {
                        val eventTitle = eventCursor.getString(eventCursor.getColumnIndex("title"))
                        val eventDescription =
                            eventCursor.getString(eventCursor.getColumnIndex("description"))
                        val dtstart = eventCursor.getLong(eventCursor.getColumnIndex("dtstart"))
                        if (!TextUtils.isEmpty(title) && title == eventTitle && !TextUtils.isEmpty(
                                description
                            ) && description == eventDescription && dtstart == startTime
                        ) {
                            val id =
                                eventCursor.getInt(eventCursor.getColumnIndex(CalendarContract.Calendars._ID)) //取得id
                            val deleteUri =
                                ContentUris.withAppendedId(Uri.parse(calanderEventURL), id.toLong())
                            val rows = context.contentResolver.delete(deleteUri, null, null)
                            if (rows == -1) {
                                // 删除提醒失败直接返回
                                callback?.onFailed(onCalendarRemindListener.Status._REMIND_ERROR)
                                return
                            }
                            //删除提醒成功
                            callback?.onSuccess()
                        }
                        eventCursor.moveToNext()
                    }
                }
            }
    }

    /**
     * 辅助方法：获取设置时间起止时间的对应毫秒数
     *
     * @param year
     * @param month  1-12
     * @param day    1-31
     * @param hour   0-23
     * @param minute 0-59
     * @return
     */
    fun remindTimeCalculator(year: Int, month: Int, day: Int, hour: Int, minute: Int): Long {
        val calendar = Calendar.getInstance()
        calendar[year, month - 1, day, hour] = minute
        return calendar.timeInMillis
    }

    /**
     * 日历提醒添加成功与否监控器
     */
    interface onCalendarRemindListener {
        enum class Status {
            _CALENDAR_ERROR, _EVENT_ERROR, _REMIND_ERROR
        }

        fun onFailed(error_code: Status?)
        fun onSuccess()
    }

}