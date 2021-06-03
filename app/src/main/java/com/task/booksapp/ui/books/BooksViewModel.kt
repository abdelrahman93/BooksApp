package com.task.booksapp.ui.books

import android.Manifest
import android.util.Log
import android.widget.Toast
import com.google.android.material.tabs.TabLayoutMediator
import com.task.booksapp.base.BaseViewModel
import com.task.booksapp.data.Loader
import com.task.booksapp.data.model.BookItem
import com.task.booksapp.di.module.SCHEDULER_IO
import com.task.booksapp.di.module.SCHEDULER_MAIN_THREAD
import com.task.booksapp.domain.usecase.books.BooksUseCase
import com.task.booksapp.utilities.FileDownloader
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.BackpressureStrategy
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit
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

    private var disposable = Disposables.disposed()
    private val fileDownloader by lazy {
        FileDownloader(
            OkHttpClient.Builder().build()
        )
    }



    fun getBooksList(jsonFileString: String) {
        subscribe(request = booksUseCase.getBooksList(jsonFileString),
            loader = Loader.Progress(true),
            success = io.reactivex.functions.Consumer { booksList ->
                internalState.value = BooksViewState.SuccessGetBooksList(booksList)
            }
        )
    }

    fun download(URL:String,cacheDir:File,fileName:String,pos:Int){
        val targetFile = File(cacheDir, fileName)

        disposable = fileDownloader.download(URL, targetFile)
            .throttleFirst(1, TimeUnit.SECONDS)
            .toFlowable(BackpressureStrategy.LATEST)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("TAG", "download:$it$fileName")
                internalState.value = BooksViewState.FileDownloading(it,pos)
            }, {
                Log.d("TAG", "download:error"+it.localizedMessage)
            }, {
                internalState.value = BooksViewState.FileDownloaded(100,pos,targetFile)
            })


    }

    fun checkPermission(rxPermissions: RxPermissions, bookItem: BookItem, position: Int) {
        compositeDisposable.add(
            rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe { granted ->
                    if (granted) {
                        internalState.value = BooksViewState.StorageGranted(bookItem, position)
                    } else {
                        internalState.value = BooksViewState.StorageDenied
                    }
                }
        )
    }


    override fun onCleared() {
        super.onCleared()
        disposable.dispose()

    }

}