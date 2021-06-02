package com.task.booksapp.ui.books

import androidx.recyclerview.widget.LinearLayoutManager
import com.task.booksapp.R
import com.task.booksapp.base.BaseFragment
import com.task.booksapp.base.BaseViewState
import com.task.booksapp.di.component.DaggerAppComponent
import com.task.booksapp.ui.books.adapter.BooksAdapter


class BooksFragment : BaseFragment<BooksViewModel>(BooksViewModel::class.java) {


    private var layoutManager: LinearLayoutManager? = null

    private lateinit var adapter: BooksAdapter

    override fun injectDagger() = DaggerAppComponent.factory()
        .create(requireContext())
        .inject(this)


    override fun getLayout() = R.layout.fragment_books

    override fun initView() {

    }

    override fun renderView(viewState: BaseViewState?) {

    }

    override fun startRequest() {

    }

    override fun actions() {

    }


}








