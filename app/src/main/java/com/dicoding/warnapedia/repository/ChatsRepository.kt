package com.dicoding.warnapedia.repository

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dicoding.warnapedia.R
import com.dicoding.warnapedia.data.Chat
import com.dicoding.warnapedia.data.localdatabase.*
import com.dicoding.warnapedia.data.network.retrofit.ApiConfig
import com.dicoding.warnapedia.data.network.request.RequestData
import com.dicoding.warnapedia.data.network.response.ResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ChatsRepository(application: FragmentActivity) {
    private val mChatsDao: ChatsDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private val context = application
    init {
        val db = LocalRoomDatabase.getDatabase(application)
        mChatsDao = db.chatDao()
    }
    fun getChat(): LiveData<List<Chats>> = mChatsDao.getChat()
    fun insert(chats: Chats) {
        executorService.execute {
            mChatsDao.insert(chats)
        }
    }
    fun delete() {
        executorService.execute { mChatsDao.delete() }
    }

    fun postChat(string: String): LiveData<Chat> {
        val result = MediatorLiveData<Chat>()
        val client = ApiConfig.getApiService().postString(RequestData(string))
        client.enqueue(object : Callback<ResponseData> {
            override fun onResponse(
                call: Call<ResponseData>,
                response: Response<ResponseData>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        result.value = Chat(
                            type = responseBody.chat.type,
                            message = responseBody.chat.message,
                            colorPalette = responseBody.chat.colorPalette,
                        )
                    }
                } else {
                    Log.e("API POST CHAT RESPONSE FAIL", "onResponseFailed: ${response.message()}")
                    result.value = Chat(1, context.resources.getString(R.string.error_response_server_down), null)
                }
            }
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.e("API POST CHAT ONFAILURE", "onFailure: ${t.message.toString()}")
                result.value = Chat(1, context.resources.getString(R.string.error_response_no_connection), null)
            }
        })
        return result
    }
}



