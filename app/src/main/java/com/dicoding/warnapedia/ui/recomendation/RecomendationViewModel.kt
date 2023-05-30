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

    private val _currentColorPalette = MutableLiveData<ColorPalette>()
    val currentColorPalette: LiveData<ColorPalette> = _currentColorPalette

    private val _currentDesign = MutableLiveData<Int>()
    val currentDesign: LiveData<Int> = _currentDesign

    private val mFavoriteColorPaletteRepository: FavoriteColorPaletteRepository = FavoriteColorPaletteRepository(application)

    fun getCurrentColorPalette(): ColorPalette{
        return _currentColorPalette.value ?: ColorPalette()
    }
    fun setCurrentColorPalette(color_palette: ColorPalette){
        _currentColorPalette.value = color_palette
    }
    fun getCurrentDesign(): Int{
        return _currentDesign.value ?: 0
    }
    fun setCurrentDesign(int: Int){
        _currentDesign.value = int
    }
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