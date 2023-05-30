package com.dicoding.warnapedia.ui.detail

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.warnapedia.R
import com.dicoding.warnapedia.data.ColorPalette
import com.dicoding.warnapedia.databinding.FragmentDetailBinding
import com.dicoding.warnapedia.helper.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null

    private val binding get() = _binding!!

    private val detailViewModel by activityViewModels<DetailViewModel>{
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val colors = DetailFragmentArgs.fromBundle(arguments as Bundle).colors
        val color_palette_name = DetailFragmentArgs.fromBundle(arguments as Bundle).colorPaletteName
        val from_page = DetailFragmentArgs.fromBundle(arguments as Bundle).fromPage

        setExampleDesignColor(colors)

        if(activity is AppCompatActivity){
            val mainActivity = (activity as? AppCompatActivity)
            mainActivity?.setSupportActionBar(binding.toolbar)
            mainActivity?.supportActionBar?.title = color_palette_name
            mainActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            val navView = mainActivity?.findViewById<BottomNavigationView>(R.id.nav_view)
            navView?.visibility = View.GONE
        }

        val layoutManager = LinearLayoutManager(activity)
        binding.rvColors.layoutManager = layoutManager
        binding.rvColors.setHasFixedSize(true)

        val adapter = DetailAdapter(colors)
        binding.rvColors.adapter = adapter

        if (from_page == resources.getString(R.string.FAVORITE)){
            setHasOptionsMenu(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        inflater.inflate(R.menu.detail_menu, menu)
        val favorite = menu.findItem(R.id.favorite)
        favorite.isChecked = true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite -> {
                if (item.isChecked == true){
                    item.setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.baseline_favorite_border_24))
                    item.isChecked = false
                }else {
                    item.setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.baseline_favorite_24))
                    item.isChecked = true
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun setExampleDesignColor(colorStr: Array<String>){
        val defaultColor = ContextCompat.getColor(requireContext(), R.color.F5F5F5)
        ViewCompat.setBackgroundTintList(binding.comp11, ColorStateList.valueOf(toColor(colorStr[0], defaultColor)))
        ViewCompat.setBackgroundTintList(binding.comp12, ColorStateList.valueOf(toColor(colorStr[0], defaultColor)))
        ViewCompat.setBackgroundTintList(binding.comp13, ColorStateList.valueOf(toColor(colorStr[0], defaultColor)))
        ViewCompat.setBackgroundTintList(binding.comp14, ColorStateList.valueOf(toColor(colorStr[0], defaultColor)))
        binding.comp15.setTextColor(toColor(colorStr[0], defaultColor))
        ViewCompat.setBackgroundTintList(binding.comp16, ColorStateList.valueOf(toColor(colorStr[0], defaultColor)))
        ViewCompat.setBackgroundTintList(binding.comp17, ColorStateList.valueOf(toColor(colorStr[0], defaultColor)))
        binding.comp18.setTextColor(toColor(colorStr[0], defaultColor))
        binding.comp2.setTextColor(toColor(colorStr[1], defaultColor))
        ViewCompat.setBackgroundTintList(binding.comp3, ColorStateList.valueOf(toColor(colorStr[2], defaultColor)))
        binding.lExampleDesign.setBackgroundColor(toColor(colorStr[2], defaultColor))
        ViewCompat.setBackgroundTintList(binding.comp4, ColorStateList.valueOf(toColor(colorStr[3], defaultColor)))
    }

    fun toColor(color: String, defColor: Int) : Int{
        if (color.isNullOrEmpty()) return defColor
        return Color.parseColor(color)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}