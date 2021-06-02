package com.task.booksapp.ui.books

import com.task.booksapp.base.BaseViewModel
import com.task.booksapp.data.Loader
import com.task.booksapp.di.module.SCHEDULER_IO
import com.task.booksapp.di.module.SCHEDULER_MAIN_THREAD
import com.task.booksapp.domain.usecase.books.BooksUseCase
import com.task.booksapp.ui.books.BooksViewState
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by Abdelrahman Sherif on 3/30/2021.
 */
class BooksViewModel @Inject constructor(
    private val booksUseCase: BooksUseCase,
    @Named(SCHEDULER_IO) private val schedulerIo: Scheduler,
    @Named(SCHEDULER_MAIN_THREAD) private val schedulerMain: Scheduler
) : BaseViewModel(schedulerIo, schedulerMain) {


    fun getBooksList(jsonFileString: String) {
        subscribe(request = booksUseCase.getBooksList(jsonFileString),
            loader = Loader.Progress(true),
            success = io.reactivex.functions.Consumer { booksList ->
                internalState.value = BooksViewState.successGetBooksList(booksList)
            }
        )
    }



}