package com.dicoding.warnapedia.data.localdatabase

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.dicoding.warnapedia.data.ColorPalette
import kotlinx.parcelize.Parcelize

@Entity(tableName = "Chats")
@Parcelize
data class Chats(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "type")
    var type: Int? = null,

    @ColumnInfo(name = "message")
    var message: String? = null,

    @ColumnInfo(name = "colorPalette")
    @TypeConverters(ColorPaletteTypeConverter::class)
    var colorPalette: List<ColorPalette>? = null,
) : Parcelable