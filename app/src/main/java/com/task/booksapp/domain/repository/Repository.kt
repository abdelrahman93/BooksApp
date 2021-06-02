package com.task.booksapp.domain.repository

import com.task.booksapp.data.model.BooksList
import io.reactivex.Observable


interface Repository {
    fun getBooksList(jsonFileString: String): Observable<BooksList>
}