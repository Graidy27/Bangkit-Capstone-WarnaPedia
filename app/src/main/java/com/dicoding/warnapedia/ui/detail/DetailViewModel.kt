package com.dicoding.warnapedia.ui.detail

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.warnapedia.data.localdatabase.FavoriteColorPalette
import com.dicoding.warnapedia.repository.FavoriteColorPaletteRepository

class DetailViewModel(application: FragmentActivity) : ViewModel() {
    private val _currentDesign = MutableLiveData<Int>()
    val currentDesign: LiveData<Int> = _currentDesign
    private val mFavoriteColorPaletteRepository: FavoriteColorPaletteRepository = FavoriteColorPaletteRepository(application)

    fun getCurrentDesign(): Int{
        return _currentDesign.value ?: 0
    }
    fun setCurrentDesign(int: Int){
        _currentDesign.value = int
    }

    fun insertFavorite(colorPalette: FavoriteColorPalette) {
        mFavoriteColorPaletteRepository.insert(colorPalette)
    }

    fun deleteFavorite(colorPalette: FavoriteColorPalette) {
        mFavoriteColorPaletteRepository.delete(colorPalette)
    }
}