package com.dicoding.warnapedia.data.network.response

import com.dicoding.warnapedia.data.Chat
import com.google.gson.annotations.SerializedName

data class ResponseData(
	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("chat")
	val chat: Chat
)
