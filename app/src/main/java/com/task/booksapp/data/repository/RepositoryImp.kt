package com.task.booksapp.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.task.booksapp.data.model.BookItem
import com.task.booksapp.data.model.BooksList
import com.task.booksapp.domain.repository.Repository
import io.reactivex.Observable
import java.lang.reflect.Type

class RepositoryImp : Repository {

    override fun getBooksList(jsonFileString: String): Observable<BooksList> {
        val listBooksType: Type = object : TypeToken<ArrayList<BookItem?>?>() {}.type
        val model: ArrayList<BookItem?>? = Gson().fromJson(jsonFileString, listBooksType)
        val booksList = BooksList(books = model)
        return Observable.just(booksList)
    }

}