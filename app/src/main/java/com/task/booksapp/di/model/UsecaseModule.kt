package com.task.booksapp.di.module

import com.task.booksapp.domain.repository.Repository
import com.task.booksapp.domain.usecase.books.BooksUseCase
import com.task.booksapp.domain.usecase.books.BooksUseCaseImp
import dagger.Module
import dagger.Provides

@Module
class UsecaseModule {

    @Provides
    fun provideBooksUseCase(repository: Repository): BooksUseCase {
        return BooksUseCaseImp(repository = repository)
    }

}