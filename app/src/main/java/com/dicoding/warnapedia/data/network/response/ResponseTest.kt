package com.dicoding.warnapedia.data.network.response

import com.google.gson.annotations.SerializedName

data class ResponseTest (
    @field:SerializedName("Page")
    var page: String,

    @field:SerializedName("Message")
    var message: String,

    @field:SerializedName("Timestamp")
    var timestamp: Double,
)