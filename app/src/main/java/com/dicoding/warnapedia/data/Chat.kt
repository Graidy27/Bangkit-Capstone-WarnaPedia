package com.dicoding.warnapedia.data

import com.google.gson.annotations.SerializedName

data class Chat(
    @field:SerializedName("type")
    var type: Int = 0,

    @field:SerializedName("message")
    var message: String = "",

    @field:SerializedName("colorPalette")
    var colorPalette: List<ColorPalette>? = null,
)
