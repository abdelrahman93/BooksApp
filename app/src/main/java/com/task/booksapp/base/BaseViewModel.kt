package com.task.booksapp.base

import androidx.lifecycle.ViewModel
import com.task.booksapp.data.Loader
import com.task.booksapp.utilities.SingleLiveEvent
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer

abstract class BaseViewModel(
    var subscribeOnScheduler: Scheduler?,
    var observeOnScheduler: Scheduler?
) : ViewModel() {


    var internalState = SingleLiveEvent<BaseViewState>()
    var compositeDisposable = CompositeDisposable()


    fun <T> subscribe(
        request: Observable<T>?,
        success: Consumer<T>,
        error: Consumer<Throwable> = Consumer {},
        loader: Loader = Loader.Progress(false)
    ) {

        showLoader(loader, true)
        if (request != null) {
            compositeDisposable.add(
                request.subscribeOn(subscribeOnScheduler)
                    .observeOn(observeOnScheduler)
                    .doOnError {
                        internalState.value = ErrorViewState.Error(message = it.message)
                    }
                    .doFinally {
                        showLoader(loader, false)
                    }
                    .subscribe(success, error))
        }
    }


    fun showLoader(loader: Loader, show: Boolean) {
        when (loader) {
            is Loader.Shimmer -> internalState.value = Loader.Shimmer(show)
            is Loader.Progress -> internalState.value = Loader.Progress(show)
        }
    }


    override fun onCleared() {
        if (compositeDisposable.isDisposed.not()) {
            compositeDisposable.dispose()
            compositeDisposable.clear()
        }
        super.onCleared()
    }
}

