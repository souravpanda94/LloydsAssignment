package com.example.lloydsassignment.core

import java.text.SimpleDateFormat
import java.util.Locale

fun String.toReadableDate(): String? {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
    val date = inputFormat.parse(this)
    return date?.let { outputFormat.format(it) }
}