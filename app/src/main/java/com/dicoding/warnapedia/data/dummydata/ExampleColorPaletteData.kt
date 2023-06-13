package com.dicoding.warnapedia.data.dummydata

import com.dicoding.warnapedia.data.ColorPalette

object ExampleColorPaletteData {
    private var data = arrayOf(
        arrayOf(
            "Example One",
            "#FFFFFF",
            "#F1C71D",
            "#333333",
            "#B8B2B2",
        ),
        arrayOf(
            "Example Two",
            "#FFFFFF",
            "#E1A140",
            "#532200",
            "#914110"
        ),
        arrayOf(
            "Example Three",
            "#FFFFFF",
            "#F1C71D",
            "#013880",
            "#809CC0",
        ),
    )

    val listData: ArrayList<ColorPalette>
        get() {
            val list = ArrayList<ColorPalette>()
            for (aData in data) {
                val colorPalette = ColorPalette()
                colorPalette.name = aData[0]
                colorPalette.color1 = aData[1]
                colorPalette.color2 = aData[2]
                colorPalette.color3 = aData[3]
                colorPalette.color4 = aData[4]
                list.add(colorPalette)
            }
            return list
        }
}