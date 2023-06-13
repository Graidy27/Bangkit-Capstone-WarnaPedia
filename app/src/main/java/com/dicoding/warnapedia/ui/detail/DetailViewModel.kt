package com.dicoding.warnapedia.ui.detail

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.warnapedia.data.localdatabase.FavoriteColorPalette
import com.dicoding.warnapedia.repository.FavoriteColorPaletteRepository
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DetailViewModel(application: FragmentActivity) : ViewModel() {
    private val _currentDesign = MutableLiveData<Int>()
    val currentDesign: LiveData<Int> = _currentDesign
    private val mFavoriteColorPaletteRepository: FavoriteColorPaletteRepository = FavoriteColorPaletteRepository(application)
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

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

    fun updateFavoriteColorPaletteName(new_name: String, id: Int): LiveData<Boolean>{
        val resultLiveData = MutableLiveData<Boolean>()
        executorService.submit {
            try {
                val rowsUpdated = mFavoriteColorPaletteRepository.updateFavoriteColorPaletteName(new_name,id).get()
                if (rowsUpdated > 0){
                    resultLiveData.postValue(true)
                }else {
                    resultLiveData.postValue(false)
                }
            } catch (e: Exception) {
                // Handle any exceptions
                resultLiveData.postValue(false)
            }
        }
        return resultLiveData
    }
}