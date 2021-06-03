package com.task.booksapp.utilities

import android.app.Activity
import android.content.Context
import java.io.File
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


fun saveFileInStorage(context: Activity?, path: String): File {
    val filePath = context?.getExternalFilesDir(context.callingPackage)
        .toString() + "/Downloaded Files"
    val fileDirectory = File(filePath)
    fileDirectory.mkdirs()
    val fileSrc = File(path)
    val fileDest = File(fileDirectory.path)
    fileSrc.copyTo(fileDest, true)
    return fileDest
}

