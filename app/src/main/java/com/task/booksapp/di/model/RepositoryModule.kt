package com.task.booksapp.di.module

import com.task.booksapp.domain.repository.Repository
import com.task.booksapp.data.repository.RepositoryImp
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideRepository(): Repository {
        return RepositoryImp()
    }


}