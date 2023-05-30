package com.dicoding.warnapedia.data

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
                colorPalette.colorPaletteName = aData[0]
                colorPalette.colorOne = aData[1]
                colorPalette.colorTwo = aData[2]
                colorPalette.colorThree = aData[3]
                colorPalette.colorFour = aData[4]
                list.add(colorPalette)
            }
            return list
        }
}