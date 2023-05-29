package com.dicoding.warnapedia.data

import com.google.gson.annotations.SerializedName

data class ColorPalette(
    @field:SerializedName("color_palette_name")
    var colorPaletteName: String = "",

    @field:SerializedName("color_one")
    var colorOne: String = "",

    @field:SerializedName("color_two")
    var colorTwo: String = "",

    @field:SerializedName("color_three")
    var colorThree: String = "",

    @field:SerializedName("color_four")
    var colorFour: String = "",
)
