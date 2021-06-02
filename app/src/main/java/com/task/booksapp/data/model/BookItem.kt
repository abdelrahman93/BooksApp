package com.task.booksapp.data.model

import com.google.gson.annotations.SerializedName

data class BookItem(
	@field:SerializedName("id")
	val id: Int? = null,
	@field:SerializedName("type")
	val type: String = "",
	@field:SerializedName("url")
	val url: String = "",
	@field:SerializedName("name")
	val name: String = ""
)
