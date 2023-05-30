package com.dicoding.warnapedia.ui.chat.chatdetail

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.warnapedia.data.Chat
import com.dicoding.warnapedia.data.ColorPalette

class ChatDetailViewModel(application: FragmentActivity) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _listChat = MutableLiveData<List<Chat>>()
    val listChat: LiveData<List<Chat>> = _listChat

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
}