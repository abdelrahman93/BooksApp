package com.task.booksapp.di.component

import android.content.Context
import com.task.booksapp.ui.books.BooksFragment
import com.task.booksapp.di.module.*
import dagger.BindsInstance
import dagger.Component
import io.reactivex.Scheduler
import javax.inject.Named
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        RepositoryModule::class,
        SchedulerModule::class,
        UsecaseModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {
    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(booksFragment: BooksFragment)


    @Named(SCHEDULER_MAIN_THREAD)
    fun getMainThread(): Scheduler

    @Named(SCHEDULER_IO)
    fun getIOThread(): Scheduler

}