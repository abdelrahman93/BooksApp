package com.task.booksapp.data

import com.task.booksapp.base.BaseViewState

sealed class Loader : BaseViewState() {
    data class Shimmer(var show: Boolean) : Loader()
    data class Progress(var show: Boolean) : Loader()
}


