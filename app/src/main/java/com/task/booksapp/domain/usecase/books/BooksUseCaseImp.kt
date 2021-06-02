package com.task.booksapp.domain.usecase.books

import com.task.booksapp.data.model.BooksList
import com.task.booksapp.domain.repository.Repository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by Abdelrahman Sherif on 1/6/2021.
 */
class BooksUseCaseImp @Inject

constructor(val repository: Repository) : BooksUseCase {

    override fun getBooksList(jsonFileString: String)
            : Observable<BooksList> {
        return repository.getBooksList(jsonFileString)
    }

}