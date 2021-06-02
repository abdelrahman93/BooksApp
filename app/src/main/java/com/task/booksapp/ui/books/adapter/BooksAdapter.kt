package com.task.booksapp.ui.books.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.booksapp.R
import com.task.booksapp.data.model.BookItem
import kotlinx.android.synthetic.main.item_book.view.*


class BooksAdapter(
    private val booksList: ArrayList<BookItem?>?,
    private val onClick: (BookItem) -> Unit
) :
    RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {

    override fun getItemCount() = booksList?.size!!


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false)
        return BooksViewHolder(view)
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        booksList?.get(position)?.let { holder.bind(it) }
    }


    inner class BooksViewHolder(private val rootView: View) : RecyclerView.ViewHolder(rootView) {
        fun bind(bookItem: BookItem) {
            this.rootView.tvBookTitle.text = bookItem.name
            this.rootView.tvBookType.text = bookItem.type

            this.rootView.setOnClickListener {
                onClick.invoke(bookItem)
            }
        }
    }
}


