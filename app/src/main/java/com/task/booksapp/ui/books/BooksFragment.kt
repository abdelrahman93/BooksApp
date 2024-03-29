package com.task.booksapp.ui.books

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.StrictMode
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.booksapp.R
import com.task.booksapp.base.BaseFragment
import com.task.booksapp.base.BaseViewState
import com.task.booksapp.data.model.BookItem
import com.task.booksapp.data.model.DownloadStatusTypes
import com.task.booksapp.di.component.DaggerAppComponent
import com.task.booksapp.ui.books.adapter.BooksAdapter
import com.task.booksapp.utilities.getJsonDataFromAsset
import com.task.booksapp.utilities.saveFileInStorage
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.fragment_books.*
import java.io.File


class BooksFragment : BaseFragment<BooksViewModel>(BooksViewModel::class.java) {

    private var dataSetbooks: ArrayList<BookItem?>? = null
    private var downloadedFiles = HashMap<Int, File>()
    private var layoutManager: LinearLayoutManager? = null

    private lateinit var adapter: BooksAdapter

    override fun injectDagger() = DaggerAppComponent.factory()
        .create(requireContext())
        .inject(this)


    override fun getLayout() = R.layout.fragment_books

    override fun initView() {
        layoutManager = LinearLayoutManager(activity)
        rvBooksList.layoutManager = layoutManager
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }

    override fun renderView(viewState: BaseViewState?) {
        when (viewState) {
            is BooksViewState.SuccessGetBooksList -> {
                dataSetbooks = viewState.booksList.books
                initBooksListList(dataSetbooks)
            }

            //First status of downloading
            is BooksViewState.FileDownloading -> {
                dataSetbooks?.get(viewState.postion)?.downloadProgress = viewState.status
                dataSetbooks?.get(viewState.postion)?.downloadStatusTypes =
                    DownloadStatusTypes.Downloading

                adapter.notifyItemChanged(viewState.postion)
            }

            is BooksViewState.UpdateProgress -> {
                updateDownloadProgressBar(viewState.status, viewState.postion)
            }

            is BooksViewState.FileDownloaded -> {
                downloadedFiles[viewState.postion] =
                    saveFileInStorage(activity, viewState.downloadedFile.path)
                dataSetbooks?.get(viewState.postion)?.downloadProgress = viewState.status
                dataSetbooks?.get(viewState.postion)?.downloadStatusTypes =
                    DownloadStatusTypes.Downloaded

                adapter.notifyItemChanged(viewState.postion)
            }


            is BooksViewState.StorageGranted -> {
                activity?.cacheDir?.let {
                    viewModel.downloadFile(
                        viewState.bookItem.url,
                        it,
                        viewState.bookItem.name,
                        viewState.position
                    )
                }
            }

            is BooksViewState.StorageDenied -> {
                Toast.makeText(activity, getString(R.string.permission_error), Toast.LENGTH_LONG)
                    .show()
            }
        }


    }


    override fun startRequest() {
        getJsonDataFromAsset(requireContext())?.let { viewModel.getBooksList(it) }
    }

    override fun actions() {
    }


    private fun initBooksListList(booksList: ArrayList<BookItem?>?) {
        adapter = BooksAdapter(booksList, this::onDownloadClicked)
        rvBooksList.adapter = adapter
    }

    private fun onDownloadClicked(bookItem: BookItem, position: Int) {
        when (bookItem.downloadStatusTypes) {

            DownloadStatusTypes.ToDownload -> {
                viewModel.checkPermission(RxPermissions.getInstance(activity), bookItem, position)

            }
            DownloadStatusTypes.Downloaded -> {
                if (downloadedFiles[position] != null)
                    when (bookItem.type) {
                        BooksAdapter.VIDEO_TYPE -> {
                            playVideo(downloadedFiles[position])
                        }
                        BooksAdapter.PDF_TYPE -> {
                            openPdf(downloadedFiles[position])
                        }
                    }
            }
            DownloadStatusTypes.Downloading -> {
            }
        }
    }

    fun openPdf(file: File?) {
        val path: Uri = Uri.fromFile(file)
        val pdfIntent = Intent(Intent.ACTION_VIEW)
        pdfIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        pdfIntent.setDataAndType(path, "application/pdf")
        pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(pdfIntent)

    }

    @SuppressLint("SetWorldReadable")
    fun playVideo(file: File?) {
        file?.setReadable(true, false)
        val intentUri = Uri.fromFile(File(file?.path.toString()))
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.setDataAndType(intentUri, "video/mp4")
        startActivity(intent)

    }


    /*
     * update progress bar in recycler view
     * get viewHolder from position and progress bar from that viewHolder
     */
    private fun updateDownloadProgressBar(bookItem: Int?, position: Int) {
        val viewHolder = rvBooksList.findViewHolderForAdapterPosition(position)
        if (viewHolder != null) {
            bookItem?.let { (viewHolder as BooksAdapter.BooksViewHolder).bind(it, position) }
        }
    }

}











