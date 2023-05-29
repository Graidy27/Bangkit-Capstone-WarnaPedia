package com.dicoding.warnapedia.data.localdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteColorPalette::class], version = 1)
abstract class FavoriteColorPaletteRoomDatabase : RoomDatabase() {
    abstract fun favoriteColorPaletteDao(): FavoriteColorPaletteDao
    companion object {
        @Volatile
        private var INSTANCE: FavoriteColorPaletteRoomDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): FavoriteColorPaletteRoomDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteColorPaletteRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavoriteColorPaletteRoomDatabase::class.java, "favorite_color_palette_database")
                        .build()
                }
            }
            return INSTANCE as FavoriteColorPaletteRoomDatabase
        }
    }
}