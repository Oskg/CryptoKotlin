package com.example.cryptokotlin.utils

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun convertTimestamp(timestamp: Long?):String{
    if (timestamp == null) return ""
    val stamp = Timestamp(timestamp * 1000)
    val date = Date(stamp.time)
    val pattern = "HH:mm:ss"
    val sdf = SimpleDateFormat(pattern,Locale.getDefault())
    sdf.timeZone = TimeZone.getDefault()
    return sdf.format(date)
}