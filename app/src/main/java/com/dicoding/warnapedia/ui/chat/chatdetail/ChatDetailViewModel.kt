package com.dicoding.warnapedia.ui.chat.chatdetail

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.warnapedia.data.Chat
import com.dicoding.warnapedia.data.ColorPalette
import com.dicoding.warnapedia.data.ExampleChatData

class ChatDetailViewModel(application: FragmentActivity) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _listChat = MutableLiveData<List<Chat>>()
    val listChat: LiveData<List<Chat>> = _listChat

    private var example_index = 1

    fun addChat(str: String){
        val currentList = _listChat.value?.toMutableList() ?: mutableListOf()
        currentList.add(formatedUserChat(str))
        _listChat.value = currentList
    }

    fun getResponse(str: String){
//        val currentList = _listChat.value?.toMutableList() ?: mutableListOf()
//        currentList.add(formatedResponseChat(str))
//        _listChat.value = currentList

        val currentList = _listChat.value?.toMutableList() ?: mutableListOf()
        currentList.add(ExampleChatData.listData[example_index])
        if (example_index == 4){
            example_index = 1
        }else {
            example_index = example_index + 1
        }
        _listChat.value = currentList
    }

    fun loadChat(list_chat: ArrayList<Chat>){
        val formattedObjectList = list_chat.map {
            Chat(
                type = it.type,
                message = it.message,
                colorPalette = it.colorPalette
            )
        }
        _listChat.value = formattedObjectList
    }

    fun formatedUserChat(str: String): Chat{
        val new_chat = Chat()
        new_chat.type = 0
        new_chat.colorPalette = null
        new_chat.message = str
        return new_chat
    }

    fun formatedResponseChat(str: String): Chat{
        val new_chat = Chat()
//        new_chat.type = 0
//        new_chat.colorPalette = null
//        new_chat.message = str
        return new_chat
    }
}