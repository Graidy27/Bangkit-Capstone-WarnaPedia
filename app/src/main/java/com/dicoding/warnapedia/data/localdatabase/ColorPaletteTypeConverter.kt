package com.dicoding.warnapedia.data.localdatabase

import androidx.room.TypeConverter
import com.dicoding.warnapedia.data.ColorPalette
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ColorPaletteTypeConverter {
    @TypeConverter
    fun fromJson(json: String): List<ColorPalette>? {
        val listType = object : TypeToken<List<ColorPalette>>() {}.type
        return Gson().fromJson(json, listType)
    }

    @TypeConverter
    fun toJson(colorPaletteList: List<ColorPalette>?): String {
        return Gson().toJson(colorPaletteList)
    }
}