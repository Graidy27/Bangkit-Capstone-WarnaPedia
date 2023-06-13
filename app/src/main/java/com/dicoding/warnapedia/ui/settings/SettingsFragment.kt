package com.dicoding.warnapedia.ui.settings

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Display
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.dicoding.warnapedia.R
import com.dicoding.warnapedia.databinding.FragmentSettingsBinding
import com.dicoding.warnapedia.helper.SettingPreferences
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private lateinit var preference: SettingPreferences
    private lateinit var datastore: DataStore<Preferences>
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "config")
    private val binding get() = _binding!!
    private var isColorBlind = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        datastore = requireContext().dataStore
        preference = SettingPreferences.getInstance(datastore)

        lifecycleScope.launch {
            preference.getIsColorBlind().collect { number ->
                isColorBlind = number
            }
        }

        if(activity is AppCompatActivity){
            val mainActivity = (activity as? AppCompatActivity)
            val navView = mainActivity?.findViewById<BottomNavigationView>(R.id.nav_view)
            if (navView?.visibility == View.GONE) navView?.visibility = View.VISIBLE
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.color_blind_list,
            R.layout.item_spinner_color_blind
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spinnerColorBlindValue.adapter = adapter
        }

        var first_spinner_counter = 0
        binding.spinnerColorBlindValue.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("LOG SPINNER1", "NOTHING")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val type = parent?.getItemAtPosition(position).toString()
                if (first_spinner_counter < 1) {
                    Log.d("LOG SPINNER2", "NOTHING")
                    parent?.setSelection(isColorBlind)
                    first_spinner_counter =+ 1
                } else {
                    Log.d("LOG SPINNER3", "$type, $position")
                    lifecycleScope.launch {
                        preference.setIsColorBlind(position)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}