package com.task.booksapp.data.model

sealed class DownloadStatusTypes {
    object ToDownload : DownloadStatusTypes()
    object Downloading : DownloadStatusTypes()
    object Downloaded : DownloadStatusTypes()
}