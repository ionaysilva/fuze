package com.example.pandaapplication.extensions

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


fun String.loadTextDate(): String {
    val today = Date()
    val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val date = input.parse(this)

    val todayCalendar = Calendar.getInstance()
    todayCalendar.time = today

    val dateCalendar = Calendar.getInstance()
    dateCalendar.time = date


    date?.let {
        if (DateUtils.isToday(date.time)) {
            val output = SimpleDateFormat("HH:mm", Locale.getDefault())
            return "Hoje " + output.format(date)
        } else if (todayCalendar.weekYear == dateCalendar.weekYear ){
            val output = SimpleDateFormat("EEE, HH:mm", Locale.getDefault())
            return output.format(date)
        } else {
            val output = SimpleDateFormat("dd.MM HH:mm", Locale.getDefault())
            return output.format(date)
        }
    }
    return ""
}

