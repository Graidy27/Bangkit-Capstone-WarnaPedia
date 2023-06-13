package com.dicoding.warnapedia.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ColorPalette(
    @field:SerializedName("id")
    var id: Int = 0,

    @field:SerializedName("name")
    var name: String = "",

    @field:SerializedName("color1")
    var color1: String = "",

    @field:SerializedName("color2")
    var color2: String = "",

    @field:SerializedName("color3")
    var color3: String = "",

    @field:SerializedName("color4")
    var color4: String = "",
) : Parcelable
