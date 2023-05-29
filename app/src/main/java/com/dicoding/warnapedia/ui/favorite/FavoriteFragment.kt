package com.dicoding.warnapedia.ui.favorite

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.warnapedia.R
import com.dicoding.warnapedia.data.ColorPalette
import com.dicoding.warnapedia.data.ExampleColorPaletteData
import com.dicoding.warnapedia.data.localdatabase.FavoriteColorPalette
import com.dicoding.warnapedia.databinding.FragmentFavoriteBinding
import com.dicoding.warnapedia.helper.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton

class FavoriteFragment : Fragment() {

    private lateinit var adapter: FavoriteAdapter

    private var _binding: FragmentFavoriteBinding? = null

    private val binding get() = _binding!!

    private val favoriteViewModel by activityViewModels<FavoriteViewModel>{
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        adapter = FavoriteAdapter(listOf(), requireActivity())
        binding.rvColorPalette.adapter = adapter
        return binding.root
    }

    @SuppressLint("Range")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(activity)
        binding.rvColorPalette.layoutManager = layoutManager
        binding.rvColorPalette.setHasFixedSize(true)

        favoriteViewModel.listColorPalette.observe(viewLifecycleOwner) { listColorPalette ->
            adapter.updateData(listColorPalette)
            adapter.notifyDataSetChanged()
            if (listColorPalette.isNotEmpty()){
                setExampleDesignColor(listColorPalette[0])
            }
        }
        favoriteViewModel.loadFavoriteColorPalette(viewLifecycleOwner)

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClick(data: ColorPalette) {
                setExampleDesignColor(data)
            }
        })

        if(activity is AppCompatActivity){
            val mainActivity = (activity as? AppCompatActivity)
            val navView = mainActivity?.findViewById<BottomNavigationView>(R.id.nav_view)
            if (navView?.visibility == View.GONE) navView?.visibility = View.VISIBLE
        }
    }

    fun setExampleDesignColor(colorStr: ColorPalette){
        val defaultColor = ContextCompat.getColor(requireContext(), R.color.F5F5F5)
        ViewCompat.setBackgroundTintList(binding.comp11, ColorStateList.valueOf(toColor(colorStr.colorOne, defaultColor)))
        ViewCompat.setBackgroundTintList(binding.comp12, ColorStateList.valueOf(toColor(colorStr.colorOne, defaultColor)))
        ViewCompat.setBackgroundTintList(binding.comp13, ColorStateList.valueOf(toColor(colorStr.colorOne, defaultColor)))
        ViewCompat.setBackgroundTintList(binding.comp14, ColorStateList.valueOf(toColor(colorStr.colorOne, defaultColor)))
        binding.comp15.setTextColor(toColor(colorStr.colorOne, defaultColor))
        ViewCompat.setBackgroundTintList(binding.comp16, ColorStateList.valueOf(toColor(colorStr.colorOne, defaultColor)))
        ViewCompat.setBackgroundTintList(binding.comp17, ColorStateList.valueOf(toColor(colorStr.colorOne, defaultColor)))
        binding.comp18.setTextColor(toColor(colorStr.colorOne, defaultColor))
        binding.comp2.setTextColor(toColor(colorStr.colorTwo, defaultColor))
        ViewCompat.setBackgroundTintList(binding.comp3, ColorStateList.valueOf(toColor(colorStr.colorThree, defaultColor)))
        binding.lExampleDesign.setBackgroundColor(toColor(colorStr.colorThree, defaultColor))
        ViewCompat.setBackgroundTintList(binding.comp4, ColorStateList.valueOf(toColor(colorStr.colorFour, defaultColor)))
    }

    fun toColor(color: String, defColor: Int) : Int{
        if (color.isNullOrEmpty()) return defColor
        return Color.parseColor(color)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}