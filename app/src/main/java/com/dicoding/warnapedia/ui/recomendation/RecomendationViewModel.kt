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

    fun getCurrentColorPalette(): ColorPalette {
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
    fun insertFavorite(colorPalette: FavoriteColorPalette) {
        mFavoriteColorPaletteRepository.insert(colorPalette)
    }

    fun deleteFavorite(colorPalette: FavoriteColorPalette) {
        mFavoriteColorPaletteRepository.delete(colorPalette)
    }

    fun loadColorPalette(list_color_palette: ArrayList<ColorPalette>){
        val formattedObjectList = list_color_palette.map {
            ColorPalette(
                name =  it.name,
                color1 = it.color1,
                color2 = it.color2,
                color3 = it.color3,
                color4 = it.color4,
            )
        }
        _listColorPalette.value = formattedObjectList
    }
}