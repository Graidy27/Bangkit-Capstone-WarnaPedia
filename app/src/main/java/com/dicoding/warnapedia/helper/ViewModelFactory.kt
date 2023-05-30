package com.dicoding.warnapedia.helper

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.warnapedia.ui.chat.chatdetail.ChatDetailViewModel
import com.dicoding.warnapedia.ui.detail.DetailViewModel
import com.dicoding.warnapedia.ui.favorite.FavoriteViewModel
import com.dicoding.warnapedia.ui.recomendation.RecomendationViewModel

class ViewModelFactory private constructor(private val mApplication: FragmentActivity) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(application: FragmentActivity): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(mApplication) as T
        }
        else if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(mApplication) as T
        }
        else if (modelClass.isAssignableFrom(RecomendationViewModel::class.java)){
            return RecomendationViewModel(mApplication) as T
        }
        else if (modelClass.isAssignableFrom(ChatDetailViewModel::class.java)){
            return ChatDetailViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}