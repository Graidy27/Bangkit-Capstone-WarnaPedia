package com.dicoding.warnapedia.data

object ExampleChatData {
    private var data = arrayOf(
        arrayOf(
            0,
            "What do you want to create today?",
            null
        ),
        arrayOf(
            0,
            "Please provide more information!",
            null
        ),
        arrayOf(
            1,
            "Here are the design that we come up to, enjoy",
            listOf(
                ColorPalette("Example One", "#FFFFFF", "#F1C71D","#333333","#B8B2B2"),
                ColorPalette("Example Two", "#FFFFFF", "#E1A140","#532200","#914110"),
                ColorPalette("Example Three", "#FFFFFF", "#F1C71D","#013880","#809CC0")
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