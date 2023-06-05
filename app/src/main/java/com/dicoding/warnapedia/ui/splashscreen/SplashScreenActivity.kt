package com.dicoding.warnapedia.ui.splashscreen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.dicoding.warnapedia.R
import com.dicoding.warnapedia.helper.SettingPreferences
import com.dicoding.warnapedia.ui.MainActivity
import com.dicoding.warnapedia.ui.getstarted.GetStartedActivity
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var preference: SettingPreferences
    private lateinit var datastore: DataStore<Preferences>
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "config")
    private var isFirstOpened = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        datastore = this.dataStore
        preference = SettingPreferences.getInstance(datastore)
        getPreferenceSetting()

//        savePreferenceSetting(true)

        Handler(Looper.getMainLooper()).postDelayed({
            if (isFirstOpened) {
                startActivity(Intent(this, GetStartedActivity::class.java))
                savePreferenceSetting(false)
            }else {
                startActivity(Intent(this, MainActivity::class.java))
            }
            finish()
        }, 1000)
    }

    private fun savePreferenceSetting(boolean: Boolean){
        lifecycleScope.launch {
            preference.savePreferenceSetting(boolean)
        }
    }
    private fun getPreferenceSetting(){
        lifecycleScope.launch {
            preference.getPreferenceSetting().collect { boolean ->
                isFirstOpened = boolean
            }
        }
    }
}