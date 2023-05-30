package com.dicoding.warnapedia.data.localdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChatsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(chats: Chats)

    @Query("DELETE FROM Chats")
    fun delete()

    @Query("SELECT * FROM Chats ORDER BY id ASC")
    fun getChat(): LiveData<List<Chats>>
}