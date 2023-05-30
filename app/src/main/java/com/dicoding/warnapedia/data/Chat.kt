package com.dicoding.warnapedia.data

import com.google.gson.annotations.SerializedName

data class Chat(
    @field:SerializedName("type")
    var colorPaletteName: Int = 0,

    @field:SerializedName("chat")
    var colorOne: String = "",

    @field:SerializedName("color_palette")
    var colorPalette: String = "",
)
