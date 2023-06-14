package com.dicoding.warnapedia.ui.detail

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.warnapedia.R
import com.dicoding.warnapedia.data.ColorPalette
import com.dicoding.warnapedia.data.localdatabase.FavoriteColorPalette
import com.dicoding.warnapedia.repository.FavoriteColorPaletteRepository
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DetailViewModel(application: FragmentActivity) : ViewModel() {
    private val _currentDesign = MutableLiveData<Int>()
    val currentDesign: LiveData<Int> = _currentDesign
    private val mFavoriteColorPaletteRepository: FavoriteColorPaletteRepository = FavoriteColorPaletteRepository(application)
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private val context = application
    var characterMaxLength = 20

    fun getCurrentDesign(): Int{
        return _currentDesign.value ?: 1
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

    fun layoutToBitmap(layout: View): Bitmap {
        val bitmap = Bitmap.createBitmap(layout.width, layout.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        layout.draw(canvas)
        return bitmap
    }

    fun shareColor(layout: View, colors: ColorPalette){
        val bitmap = layoutToBitmap(layout)
        val file = File(context?.cacheDir, context.resources.getString(R.string.layout_image))
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
        outputStream.close()

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(context, "com.dicoding.warnapedia", file))
            putExtra(Intent.EXTRA_TEXT, context.resources.getString(R.string.share_design_text, colors.color1, colors.color2, colors.color3, colors.color4))
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        val chooserIntent = Intent.createChooser(shareIntent, context.resources.getString(R.string.share_layout_image))
        context.startActivity(chooserIntent)
    }
}