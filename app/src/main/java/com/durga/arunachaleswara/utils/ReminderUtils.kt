package com.durga.arunachaleswara.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.durga.arunachaleswara.model.User
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun formatDateTime(timeMillis: Long): String {
    val formatter = SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.getDefault())
    return formatter.format(timeMillis)
}

fun getOneYearCompletionDate(joinedDate: Long): Long {
    val calendar = Calendar.getInstance().apply {
        timeInMillis = joinedDate
        add(Calendar.YEAR, 1)
    }
    return calendar.timeInMillis
}

fun getReminderMessage(user: User): String {
    val joined = formatDateTime(user.createdAt.toLong())
    val completion = formatDateTime(getOneYearCompletionDate(user.createdAt.toLong()))

    return "Hello ${user.name}, this is a reminder. Your one year completion date is $completion. Joined on: $joined."
}

fun sendSms(context: Context, phoneNumber: String, message: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("smsto:$phoneNumber")
        putExtra("sms_body", message)
    }
    context.startActivity(intent)
}

fun sendEmail(context: Context, email: String, subject: String, body: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$email")
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }
    context.startActivity(intent)
}