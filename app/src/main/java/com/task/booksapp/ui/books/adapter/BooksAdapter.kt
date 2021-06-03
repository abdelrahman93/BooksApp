package com.task.booksapp.ui.books.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.task.booksapp.R
import com.task.booksapp.data.model.BookItem
import com.task.booksapp.data.model.DownloadStatusTypes
import kotlinx.android.synthetic.main.item_book.view.*
import kotlin.reflect.KFunction2


class BooksAdapter(
    private val booksList: ArrayList<BookItem?>?,
    private val onClick: KFunction2<BookItem, Int, Unit>
) :
    RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {

    override fun getItemCount() = booksList?.size!!


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false)
        return BooksViewHolder(view)
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        booksList?.get(position)?.let { holder.bind(it, position) }
    }


    inner class BooksViewHolder(private val rootView: View) : RecyclerView.ViewHolder(rootView) {
        fun bind(bookItem: BookItem?, pos: Int) {
            this.rootView.tvBookTitle.text = bookItem?.name
            this.rootView.tvBookType.text = bookItem?.type

            when (bookItem?.type) {
                VIDEO_TYPE -> {
                    this.rootView.ivBookType.background =
                        ContextCompat.getDrawable(rootView.context, R.drawable.ic_video)
                }
                PDF_TYPE -> {
                    this.rootView.ivBookType.background =
                        ContextCompat.getDrawable(rootView.context, R.drawable.ic_pdf)
                }
            }

            this.rootView.ivDownload.setOnClickListener {
                bookItem?.let { it1 -> onClick.invoke(it1, pos) }
            }

            when (bookItem?.downloadStatusTypes) {
                DownloadStatusTypes.ToDownload -> {
                    this.rootView.consProgressDownload.visibility = View.GONE
                    this.rootView.ivDownload.visibility = View.VISIBLE
                    this.rootView.ivDownload.background =
                        ContextCompat.getDrawable(rootView.context, R.drawable.ic_download)

                }
                DownloadStatusTypes.Downloading -> {
                    this.rootView.ivDownload.background =
                        ContextCompat.getDrawable(rootView.context, R.drawable.ic_download)
                    this.rootView.consProgressDownload.visibility = View.VISIBLE
                    this.rootView.pbDownload.progress = bookItem.downloadProgress
                    this.rootView.tvDownload.text =
                        String.format("%s %s", bookItem.downloadProgress.toString(), "%")
                }
                DownloadStatusTypes.Downloaded -> {
                    this.rootView.consProgressDownload.visibility = View.VISIBLE
                    this.rootView.pbDownload.progress = bookItem.downloadProgress
                    this.rootView.tvDownload.text =
                        String.format("%s %s", bookItem.downloadProgress.toString(), "%")
                    this.rootView.ivDownload.background =
                        ContextCompat.getDrawable(rootView.context, R.drawable.ic_play)
                }
            }


        }

        //Update Progress bar only
        fun bind(progress: Int, pos: Int) {
            this.rootView.pbDownload.progress = progress
            this.rootView.tvDownload.text =
                String.format("%s %s", progress.toString(), "%")
        }


    }

    companion object {
        const val VIDEO_TYPE = "VIDEO"
        const val PDF_TYPE = "PDF"
    }
}


