package com.dicoding.warnapedia.data.localdatabase

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "FavoriteColorPalette", indices = [Index(value = ["color_palette_name"], unique = true)])
@Parcelize
data class FavoriteColorPalette(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "color_palette_name")
    var colorPaletteName: String = "",

    @ColumnInfo(name = "color_one")
    var colorOne: String? = null,

    @ColumnInfo(name = "color_two")
    var colorTwo: String? = null,

    @ColumnInfo(name = "color_three")
    var colorThree: String? = null,

    @ColumnInfo(name = "color_four")
    var colorFour: String? = null,
) : Parcelable