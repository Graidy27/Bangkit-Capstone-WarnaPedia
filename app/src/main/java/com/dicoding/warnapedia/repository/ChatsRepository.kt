package com.dicoding.warnapedia.repository

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import com.dicoding.warnapedia.data.localdatabase.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ChatsRepository(application: FragmentActivity) {
    private val mChatsDao: ChatsDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
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
}