package com.task.booksapp.ui.books

import androidx.recyclerview.widget.LinearLayoutManager
import com.task.booksapp.R
import com.task.booksapp.base.BaseFragment
import com.task.booksapp.base.BaseViewState
import com.task.booksapp.data.model.BookItem
import com.task.booksapp.di.component.DaggerAppComponent
import com.task.booksapp.ui.books.adapter.BooksAdapter
import com.task.booksapp.utilities.getJsonDataFromAsset
import kotlinx.android.synthetic.main.fragment_books.*


class BooksFragment : BaseFragment<BooksViewModel>(BooksViewModel::class.java) {


    private var layoutManager: LinearLayoutManager? = null

    private lateinit var adapter: BooksAdapter

    override fun injectDagger() = DaggerAppComponent.factory()
        .create(requireContext())
        .inject(this)


    override fun getLayout() = R.layout.fragment_books

    override fun initView() {
        layoutManager = LinearLayoutManager(activity)
        rvBooksList.layoutManager = layoutManager
    }

    override fun renderView(viewState: BaseViewState?) {
        when (viewState) {
            is BooksViewState.successGetBooksList -> {
                initBooksListList(viewState.booksList.books)
            }
        }
    }

    override fun startRequest() {
        getJsonDataFromAsset(requireContext())?.let { viewModel.getBooksList(it) }
    }

    override fun actions() {

    }


    private fun initBooksListList(booksList: ArrayList<BookItem?>?) {
        adapter = BooksAdapter(booksList) {
            //onClick
        }
        rvBooksList.adapter = adapter
    }


}








