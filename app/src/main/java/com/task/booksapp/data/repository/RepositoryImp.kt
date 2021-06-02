package com.task.booksapp.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.task.booksapp.data.model.BooksList
import com.task.booksapp.domain.repository.Repository
import io.reactivex.Observable

class RepositoryImp : Repository {

    override fun getBooksList(jsonFileString: String): Observable<BooksList> {
        val gson = Gson()
        val listBooksType = object : TypeToken<BooksList>() {}.type
        val booksList: BooksList = gson.fromJson(jsonFileString, listBooksType)
        return Observable.just(booksList)
    }

}