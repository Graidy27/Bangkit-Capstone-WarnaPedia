package com.dicoding.warnapedia.repository

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import com.dicoding.warnapedia.data.localdatabase.FavoriteColorPalette
import com.dicoding.warnapedia.data.localdatabase.FavoriteColorPaletteDao
import com.dicoding.warnapedia.data.localdatabase.FavoriteColorPaletteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteColorPaletteRepository(application: FragmentActivity) {
    private val mFavoriteColorPaletteDao: FavoriteColorPaletteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = FavoriteColorPaletteRoomDatabase.getDatabase(application)
        mFavoriteColorPaletteDao = db.favoriteColorPaletteDao()
    }
    fun getFavoriteColorPaletteByName(color_palette_name: String): LiveData<FavoriteColorPalette> = mFavoriteColorPaletteDao.getFavoriteColorPaletteByName(color_palette_name)
    fun getFavoriteColorPalette(): LiveData<List<FavoriteColorPalette>> = mFavoriteColorPaletteDao.getFavoriteColorPalette()
    fun insert(color_palette: FavoriteColorPalette) {
        executorService.execute {
            mFavoriteColorPaletteDao.insert(color_palette)
        }
    }
    fun delete(color_palette: FavoriteColorPalette) {
        executorService.execute { mFavoriteColorPaletteDao.delete(color_palette.colorPaletteName) }
    }
}