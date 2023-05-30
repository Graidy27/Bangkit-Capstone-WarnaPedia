package com.dicoding.warnapedia.data.localdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [FavoriteColorPalette::class, Chats::class], version = 1)
@TypeConverters(ColorPaletteTypeConverter::class)
abstract class LocalRoomDatabase : RoomDatabase() {
    abstract fun favoriteColorPaletteDao(): FavoriteColorPaletteDao
    abstract fun chatDao(): ChatsDao
    companion object {
        @Volatile
        private var INSTANCE: LocalRoomDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): LocalRoomDatabase {
            if (INSTANCE == null) {
                synchronized(LocalRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        LocalRoomDatabase::class.java, "local_database")
                        .build()
                }
            }
            return INSTANCE as LocalRoomDatabase
        }
    }
}