package com.task.booksapp.ui.books.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.booksapp.R
import com.task.booksapp.data.model.BookItem


class BooksAdapter(
    private val booksList: ArrayList<BookItem>,
    private val onClick: (BookItem) -> Unit
) :
    RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {

    override fun getItemCount() = booksList.size

    fun addAll(newBooksList: List<BookItem>) {
        booksList.addAll(newBooksList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false)
        return BooksViewHolder(view)
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        holder.bind(booksList[position])
    }

    inner class BooksViewHolder(private val rootView: View) : RecyclerView.ViewHolder(rootView) {
        fun bind(bookItem: BookItem) {

        }
    }
}