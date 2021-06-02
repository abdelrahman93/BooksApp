package com.task.booksapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.task.booksapp.R

class BooksActivityHost : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)
    }
}