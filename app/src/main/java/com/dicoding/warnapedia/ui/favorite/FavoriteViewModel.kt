package com.dicoding.warnapedia.ui.favorite

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.warnapedia.data.ColorPalette
import com.dicoding.warnapedia.data.localdatabase.FavoriteColorPalette
import com.dicoding.warnapedia.repository.FavoriteColorPaletteRepository

class FavoriteViewModel(application: FragmentActivity) : ViewModel() {
    private val _page = MutableLiveData<String>().apply {
        value = "This is favorite Fragment"
    }
    val page: LiveData<String> = _page

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

    fun loadFavoriteColorPalette(viewLifecycleOwner: LifecycleOwner){
        mFavoriteColorPaletteRepository.getFavoriteColorPalette().observe(viewLifecycleOwner){ favColorPaletteList ->
            val formattedObjectList = favColorPaletteList.map {
                ColorPalette(
                    id = it.id,
                    name =  it.name,
                    color1 = it.color1.toString(),
                    color2 = it.color2.toString(),
                    color3 = it.color3.toString(),
                    color4 = it.color4.toString(),
                )
            }
            _listColorPalette.value = formattedObjectList
        }
    }
}