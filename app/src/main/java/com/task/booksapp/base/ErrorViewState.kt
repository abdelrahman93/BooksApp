package com.task.booksapp.base


sealed class ErrorViewState : BaseViewState() {
    data class Error(val message: String?) : BaseViewState()
}