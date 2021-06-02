package com.task.booksapp.utilities

import android.content.Context
import java.io.IOException


fun getJsonDataFromAsset(context: Context): String? {
    val jsonString: String
    try {
        jsonString = context.assets.open("books.json").bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    return jsonString
}
