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

    @Query("DELETE FROM FavoriteColorPalette WHERE color_palette_name = :color_palette")
    fun delete(color_palette: String)

    @Query("SELECT * FROM FavoriteColorPalette WHERE color_palette_name = :color_palette")
    fun getFavoriteColorPaletteByName(color_palette: String): LiveData<FavoriteColorPalette>

    @Query("SELECT * FROM FavoriteColorPalette ORDER BY color_palette_name ASC")
    fun getFavoriteColorPalette(): LiveData<List<FavoriteColorPalette>>
}