package com.task.booksapp.ui.books

import com.task.booksapp.base.BaseViewState
import com.task.booksapp.data.model.BookItem
import com.task.booksapp.data.model.BooksList
import java.io.File

/**
 * Created by Abdelrahman Sherif on 1/6/2021.
 */
class BooksViewState : BaseViewState() {

    class SuccessGetBooksList(val booksList: BooksList) : BaseViewState() {
        //For test Case comparing response body
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as SuccessGetBooksList

            if (booksList != other.booksList) return false

            return true
        }
    }

    class FileDownloading(val status: Int,val postion: Int) : BaseViewState()
    class UpdateProgress(val status: Int,val postion: Int) : BaseViewState()
    class FileDownloaded(val status: Int,val postion: Int,val downloadedFile: File) : BaseViewState()

    class StorageGranted(val bookItem: BookItem, val position: Int): BaseViewState()
    object StorageDenied: BaseViewState()

}