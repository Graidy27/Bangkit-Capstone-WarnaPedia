package com.dicoding.warnapedia.ui.chat.chatdetail

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.warnapedia.R
import com.dicoding.warnapedia.data.Chat
import com.dicoding.warnapedia.data.ExampleChatData
import com.dicoding.warnapedia.data.localdatabase.Chats
import com.dicoding.warnapedia.repository.ChatsRepository

class ChatDetailViewModel(application: FragmentActivity) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _listChat = MutableLiveData<List<Chat>>()
    val listChat: LiveData<List<Chat>> = _listChat

    private val mChatsRepository: ChatsRepository = ChatsRepository(application)

    private var example_index = 1

    fun addChat(str: String){
        val currentList = _listChat.value?.toMutableList() ?: mutableListOf()
        insertChat(formatedUserChat(str), false)
        currentList.add(formatedUserChat(str))
        _listChat.value = currentList
    }

    fun getResponse(str: String){
        val currentList = _listChat.value?.toMutableList() ?: mutableListOf()
        currentList.add(ExampleChatData.listData[example_index])
        insertChat(ExampleChatData.listData[example_index], false)
        if (example_index >= 4){
            example_index = 1
        }else {
            example_index += 1
        }
        _listChat.value = currentList
    }

    fun insertChat(chat: Chat, isFirst: Boolean) {
        if(isFirst){
            mChatsRepository.insert(Chats(0, chat.type, chat.message, chat.colorPalette))
        }else{
            mChatsRepository.insert(Chats(_listChat.value?.size ?: 0, chat.type, chat.message, chat.colorPalette))
        }
    }

    fun deleteChat() {
        example_index = 1
        mChatsRepository.delete()
    }

    fun loadChat(viewLifecycleOwner: LifecycleOwner, context: FragmentActivity){
        mChatsRepository.getChat().observe(viewLifecycleOwner){ chatList ->
            if (chatList.isNullOrEmpty()){
                val chat = Chat(1, context.resources.getString(R.string.default_first_chat), null)
                insertChat(chat, true)
                _listChat.value = listOf(chat)
            }else{
                val formattedObjectList = chatList.map { chat ->
                    Chat(
                        type = chat.type ?: 1,
                        message = chat.message.toString(),
                        colorPalette = chat.colorPalette
                    )
                }
                _listChat.value = formattedObjectList
            }
        }
    }

    fun formatedUserChat(str: String): Chat {
        val new_chat = Chat()
        new_chat.type = 0
        new_chat.colorPalette = null
        new_chat.message = str
        return new_chat
    }
}