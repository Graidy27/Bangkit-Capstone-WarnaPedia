package com.dicoding.warnapedia.data.dummydata

import com.dicoding.warnapedia.data.Chat
import com.dicoding.warnapedia.data.ColorPalette

object ExampleChatData {
    private var data = arrayOf(
        arrayOf(
            1,
            "What do you want to create today?",
            null
        ),
        arrayOf(
            1,
            "Please provide more information!",
            null
        ),
        arrayOf(
            2,
            "Here are the design that we come up to, enjoy",
            listOf(
                ColorPalette(0, "Example One", "#FFFFFF", "#F1C71D","#333333","#B8B2B2"),
                ColorPalette(1, "Example Two", "#FFFFFF", "#E1A140","#532200","#914110"),
                ColorPalette(2, "Example Three", "#FFFFFF", "#F1C71D","#013880","#809CC0")
            )
        ),
        arrayOf(
            1,
            "Please provide more information!",
            null
        ),
        arrayOf(
            2,
            "Here are the design that we come up to, enjoy",
            listOf(
                ColorPalette(0, "Example One", "#FFFFFF", "#F1C71D","#333333","#B8B2B2"),
                ColorPalette(1, "Example Two", "#FFFFFF", "#E1A140","#532200","#914110"),
                ColorPalette(2, "Example Three", "#FFFFFF", "#F1C71D","#013880","#809CC0"),
                ColorPalette(3, "Example Four", "#FF6384", "#36A2EB", "#FFCE56", "#4BC0C0"),
                ColorPalette(4, "Example Five", "#E74C3C", "#3498DB", "#F1C40F", "#27AE60"),
                ColorPalette(5, "Example Six", "#9B59B6", "#34495E", "#E67E22", "#2ECC71"),
                ColorPalette(6, "Example Seven", "#F39C12", "#16A085", "#D35400", "#8E44AD"),
                ColorPalette(7, "Example Eight", "#C0392B", "#2980B9", "#F39C12", "#27AE60"),
                ColorPalette(8, "Example Nine", "#1ABC9C", "#9B59B6", "#E74C3C", "#3498DB"),
                ColorPalette(9, "Example Ten", "#34495E", "#F1C40F", "#E67E22", "#2C3E50"),
            )
        ),
    )
    val listData: ArrayList<Chat>
        get() {
            val list = ArrayList<Chat>()
            for (aData in data) {
                val chat = Chat()
                chat.type = aData[0] as Int
                chat.message = aData[1] as String
                chat.colorPalette = aData[2] as? List<ColorPalette>?
                list.add(chat)
            }
            return list
        }
}