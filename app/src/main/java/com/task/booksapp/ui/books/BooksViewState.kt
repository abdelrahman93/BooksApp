package com.task.booksapp.ui.books

import com.task.booksapp.base.BaseViewState
import com.task.booksapp.data.model.BooksList

/**
 * Created by Abdelrahman Sherif on 1/6/2021.
 */
class BooksViewState : BaseViewState() {

    class successGetBooksList(val booksList: BooksList) : BaseViewState() {
        //For test Case comparing response body
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as successGetBooksList

            if (booksList != other.booksList) return false

            return true
        }
    }

}