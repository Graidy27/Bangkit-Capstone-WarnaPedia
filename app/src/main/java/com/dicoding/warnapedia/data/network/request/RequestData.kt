package com.dicoding.warnapedia.data.network.request

import com.google.gson.annotations.SerializedName

data class RequestData(
    @SerializedName("string")
    val string: String,

//    @SerializedName("colorblind")
//    val colorblind: String
)