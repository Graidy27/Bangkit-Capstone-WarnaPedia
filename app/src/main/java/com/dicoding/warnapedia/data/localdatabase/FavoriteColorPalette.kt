package com.dicoding.warnapedia.data.localdatabase

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "FavoriteColorPalette")
@Parcelize
data class FavoriteColorPalette(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "color1")
    var color1: String? = null,

    @ColumnInfo(name = "color2")
    var color2: String? = null,

    @ColumnInfo(name = "color3")
    var color3: String? = null,

    @ColumnInfo(name = "color4")
    var color4: String? = null,
) : Parcelable