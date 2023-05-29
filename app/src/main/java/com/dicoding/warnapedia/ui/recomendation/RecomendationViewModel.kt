package com.dicoding.warnapedia.ui.recomendation

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.warnapedia.data.ColorPalette
import com.dicoding.warnapedia.data.localdatabase.FavoriteColorPalette
import com.dicoding.warnapedia.repository.FavoriteColorPaletteRepository

class RecomendationViewModel(application: FragmentActivity) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    private val _listColorPalette = MutableLiveData<List<ColorPalette>>()
    val listColorPalette: LiveData<List<ColorPalette>> = _listColorPalette

    private val mFavoriteColorPaletteRepository: FavoriteColorPaletteRepository = FavoriteColorPaletteRepository(application)


    fun insertFavorite(user: FavoriteColorPalette) {
        mFavoriteColorPaletteRepository.insert(user)
    }

    fun deleteFavorite(user: FavoriteColorPalette) {
        mFavoriteColorPaletteRepository.delete(user)
    }

    fun loadColorPalette(list_color_palette: ArrayList<ColorPalette>){
        val formattedObjectList = list_color_palette.map {
            ColorPalette(
                colorPaletteName =  it.colorPaletteName,
                colorOne = it.colorOne,
                colorTwo = it.colorTwo,
                colorThree = it.colorThree,
                colorFour = it.colorFour,
            )
        }
        _listColorPalette.value = formattedObjectList
    }
}