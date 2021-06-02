package com.task.booksapp.domain.usecase.books

import com.task.booksapp.data.model.BooksList
import io.reactivex.Observable


/**
 * Created by Abdelrahman Sherif on 1/06/2021.
 */
interface BooksUseCase {
    fun getBooksList(jsonFileString:String): Observable<BooksList>

}