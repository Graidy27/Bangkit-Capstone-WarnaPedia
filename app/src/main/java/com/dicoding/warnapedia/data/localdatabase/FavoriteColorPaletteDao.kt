package com.dicoding.warnapedia.data.localdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteColorPaletteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteColorPalette: FavoriteColorPalette)

    @Query("DELETE FROM FavoriteColorPalette WHERE id = :id")
    fun delete(id: Int)

    @Query("SELECT * FROM FavoriteColorPalette WHERE id = :id")
    fun getFavoriteColorPaletteById(id: Int): LiveData<FavoriteColorPalette>

    @Query("SELECT * FROM FavoriteColorPalette ORDER BY id ASC")
    fun getFavoriteColorPalette(): LiveData<List<FavoriteColorPalette>>

    @Query("UPDATE FavoriteColorPalette SET name = :name WHERE id = :id")
    fun updateFavoriteColorPaletteName(name: String, id: Int): Int
}

