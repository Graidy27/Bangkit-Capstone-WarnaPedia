package com.dicoding.warnapedia.ui.chat.chatdetail

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.warnapedia.R
import com.dicoding.warnapedia.data.Chat
import com.dicoding.warnapedia.data.localdatabase.Chats
import com.dicoding.warnapedia.repository.ChatsRepository

class ChatDetailViewModel(application: FragmentActivity) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
    val context = application

    private val _listChat = MutableLiveData<List<Chat>>()
    val listChat: LiveData<List<Chat>> = _listChat

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isNewData = MutableLiveData<Boolean>()
    val isNewData: LiveData<Boolean> = _isNewData

//    private val _isError = MutableLiveData<Boolean>()
//    val isError: LiveData<Boolean> = _isError

    private val mChatsRepository: ChatsRepository = ChatsRepository(application)

    private var example_index = 1

    fun addChat(str: String){
        val currentList = _listChat.value?.toMutableList() ?: mutableListOf()
        insertChat(formatedUserChat(str))
        currentList.add(formatedUserChat(str))
        _listChat.value = currentList
    }

    fun getIsNewData(): Boolean{
        return _isNewData.value ?: false
    }

    fun setIsNewData(boolean: Boolean){
        _isNewData.value = boolean
    }

    fun getResponse(str: String, viewLifecycleOwner: LifecycleOwner){
        val currentList = _listChat.value?.toMutableList() ?: mutableListOf()
        _isLoading.value = true
        mChatsRepository.postChat(str).observe(viewLifecycleOwner) { response ->
            response.let {
                currentList.add(it)
                insertChat(it)
            }
            _isNewData.value = true
            _isLoading.value = false
        }
//        currentList.add(ExampleChatData.listData[example_index])
//        insertChat(ExampleChatData.listData[example_index], false)
//        if (example_index >= 4){
//            example_index = 1
//        }else {
//            example_index += 1
//        }
//        _listChat.value = currentList
    }

    fun insertChat(chat: Chat) {
        mChatsRepository.insert(Chats(_listChat.value?.size ?: 0, chat.type, chat.message, chat.colorPalette))
    }

    fun deleteChat() {
//        example_index = 1
        mChatsRepository.delete()
        addFirstChat()
    }

    fun loadChat(viewLifecycleOwner: LifecycleOwner){
        mChatsRepository.getChat().observe(viewLifecycleOwner){ chatList ->
            if (chatList.isNullOrEmpty()){
                addFirstChat()
            }else {
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

    fun addFirstChat(){
        val chat = Chat(1, context.resources.getString(R.string.default_first_chat), null)
        mChatsRepository.insert(Chats(0, chat.type, chat.message, chat.colorPalette))
        _listChat.value = listOf(chat)
    }

    fun formatedUserChat(str: String): Chat {
        val new_chat = Chat()
        new_chat.type = 0
        new_chat.colorPalette = null
        new_chat.message = str
        return new_chat
    }
}