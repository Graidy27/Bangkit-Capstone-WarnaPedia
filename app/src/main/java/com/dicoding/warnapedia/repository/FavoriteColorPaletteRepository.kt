package com.dicoding.warnapedia.repository

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import com.dicoding.warnapedia.data.localdatabase.FavoriteColorPalette
import com.dicoding.warnapedia.data.localdatabase.FavoriteColorPaletteDao
import com.dicoding.warnapedia.data.localdatabase.LocalRoomDatabase
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class FavoriteColorPaletteRepository(application: FragmentActivity) {
    private val mFavoriteColorPaletteDao: FavoriteColorPaletteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = LocalRoomDatabase.getDatabase(application)
        mFavoriteColorPaletteDao = db.favoriteColorPaletteDao()
    }
    fun getFavoriteColorPalette(): LiveData<List<FavoriteColorPalette>> = mFavoriteColorPaletteDao.getFavoriteColorPalette()
    fun updateFavoriteColorPaletteName(new_name: String, id: Int): Future<Int> {
        return executorService.submit( Callable {
            mFavoriteColorPaletteDao.updateFavoriteColorPaletteName(new_name, id)
        })
    }
    fun insert(color_palette: FavoriteColorPalette) {
        executorService.execute {
            mFavoriteColorPaletteDao.insert(color_palette)
        }
    }
    fun delete(color_palette: FavoriteColorPalette) {
        executorService.execute { mFavoriteColorPaletteDao.delete(color_palette.id) }
    }
}