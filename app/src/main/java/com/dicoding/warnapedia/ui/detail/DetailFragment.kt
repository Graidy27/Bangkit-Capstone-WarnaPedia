package com.dicoding.warnapedia.ui.detail

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.warnapedia.R
import com.dicoding.warnapedia.data.ColorPalette
import com.dicoding.warnapedia.data.localdatabase.FavoriteColorPalette
import com.dicoding.warnapedia.databinding.FragmentDetailBinding
import com.dicoding.warnapedia.helper.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import de.hdodenhof.circleimageview.CircleImageView

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

        detailViewModel.setCurrentDesign(1)
        detailViewModel.currentDesign.observe(viewLifecycleOwner)  { currentDesign ->
            when(currentDesign) {
                1 -> { loadWebDesign1() }
                2 -> { loadWebDesign2() }
            }
            setExampleDesignColor(ColorPalette("color_palette_name",colors[0],colors[1],colors[2],colors[3]))
        }

        binding.bLeftExampleLayout.setOnClickListener{
            when(detailViewModel.getCurrentDesign()) {
                1 -> { detailViewModel.setCurrentDesign(2) }
                2 -> { detailViewModel.setCurrentDesign(1) }
            }
        }

        binding.bRightExampleLayout.setOnClickListener{
            when(detailViewModel.getCurrentDesign()) {
                1 -> { detailViewModel.setCurrentDesign(2) }
                2 -> { detailViewModel.setCurrentDesign(1) }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        inflater.inflate(R.menu.detail_menu, menu)
        val favorite = menu.findItem(R.id.favorite)
        favorite.isChecked = true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val colors = DetailFragmentArgs.fromBundle(arguments as Bundle).colors
        val color_palette_name = DetailFragmentArgs.fromBundle(arguments as Bundle).colorPaletteName
        val curr_favorite_color_palette = FavoriteColorPalette(color_palette_name,colors[0],colors[1],colors[2],colors[3])
        return when (item.itemId) {
            R.id.favorite -> {
                if (item.isChecked == true){
                    detailViewModel.deleteFavorite(curr_favorite_color_palette)
                    item.setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.baseline_favorite_border_24))
                    item.isChecked = false
                }else {
                    detailViewModel.insertFavorite(curr_favorite_color_palette)
                    item.setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.baseline_favorite_24))
                    item.isChecked = true
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun toColor(color: String, defColor: Int) : Int{
        if (color.isNullOrEmpty()) return defColor
        return Color.parseColor(color)
    }

    private fun loadWebDesign1() {
        binding.frameLayoutExampleDesign.removeAllViews()
        layoutInflater.inflate(R.layout.example_web_design_1, binding.frameLayoutExampleDesign)
    }

    private fun loadWebDesign2() {
        binding.frameLayoutExampleDesign.removeAllViews()
        layoutInflater.inflate(R.layout.example_web_design_2, binding.frameLayoutExampleDesign)
    }
    fun setExampleDesignColor(colorStr: ColorPalette){
        when(detailViewModel.getCurrentDesign()){
            1 -> setExampleDesign1Color(colorStr)
            2 -> setExampleDesign2Color(colorStr)
        }
    }
    fun setExampleDesign1Color(colorStr: ColorPalette){
        val defaultColor = ContextCompat.getColor(requireContext(), R.color.F5F5F5)
        ViewCompat.setBackgroundTintList(binding.frameLayoutExampleDesign.findViewById(R.id.comp1_1), ColorStateList.valueOf(toColor(colorStr.colorOne, defaultColor)))
        ViewCompat.setBackgroundTintList(binding.frameLayoutExampleDesign.findViewById(R.id.comp1_2), ColorStateList.valueOf(toColor(colorStr.colorOne, defaultColor)))
        ViewCompat.setBackgroundTintList(binding.frameLayoutExampleDesign.findViewById(R.id.comp1_3), ColorStateList.valueOf(toColor(colorStr.colorOne, defaultColor)))
        ViewCompat.setBackgroundTintList(binding.frameLayoutExampleDesign.findViewById(R.id.comp1_4), ColorStateList.valueOf(toColor(colorStr.colorOne, defaultColor)))
        binding.frameLayoutExampleDesign.findViewById<TextView>(R.id.comp1_5).setTextColor(toColor(colorStr.colorOne, defaultColor))
        ViewCompat.setBackgroundTintList(binding.frameLayoutExampleDesign.findViewById(R.id.comp1_6), ColorStateList.valueOf(toColor(colorStr.colorOne, defaultColor)))
        ViewCompat.setBackgroundTintList(binding.frameLayoutExampleDesign.findViewById(R.id.comp1_7), ColorStateList.valueOf(toColor(colorStr.colorOne, defaultColor)))
        binding.frameLayoutExampleDesign.findViewById<TextView>(R.id.comp1_8).setTextColor(toColor(colorStr.colorOne, defaultColor))
        binding.frameLayoutExampleDesign.findViewById<TextView>(R.id.comp2).setTextColor(toColor(colorStr.colorTwo, defaultColor))
        ViewCompat.setBackgroundTintList(binding.frameLayoutExampleDesign.findViewById(R.id.comp3), ColorStateList.valueOf(toColor(colorStr.colorThree, defaultColor)))
        binding.frameLayoutExampleDesign.findViewById<ConstraintLayout>(R.id.l_example_design).setBackgroundColor(toColor(colorStr.colorThree, defaultColor))
        ViewCompat.setBackgroundTintList(binding.frameLayoutExampleDesign.findViewById(R.id.comp4), ColorStateList.valueOf(toColor(colorStr.colorFour, defaultColor)))
    }

    fun setExampleDesign2Color(colorStr: ColorPalette){
        val defaultColor = ContextCompat.getColor(requireContext(), R.color.F5F5F5)
        binding.frameLayoutExampleDesign.findViewById<View>(R.id.comp1).setBackgroundColor(toColor(colorStr.colorOne, defaultColor))
        binding.frameLayoutExampleDesign.findViewById<TextView>(R.id.comp2_1).setTextColor(toColor(colorStr.colorTwo, defaultColor))
        binding.frameLayoutExampleDesign.findViewById<TextView>(R.id.comp2_2).setTextColor(toColor(colorStr.colorTwo, defaultColor))
        binding.frameLayoutExampleDesign.findViewById<TextView>(R.id.comp3_1).setTextColor(toColor(colorStr.colorThree, defaultColor))
        binding.frameLayoutExampleDesign.findViewById<TextView>(R.id.comp3_2).setTextColor(toColor(colorStr.colorThree, defaultColor))
        binding.frameLayoutExampleDesign.findViewById<TextView>(R.id.comp3_3).setTextColor(toColor(colorStr.colorThree, defaultColor))
        binding.frameLayoutExampleDesign.findViewById<TextView>(R.id.comp3_4).setTextColor(toColor(colorStr.colorThree, defaultColor))
        binding.frameLayoutExampleDesign.findViewById<View>(R.id.comp3_5).setBackgroundColor(toColor(colorStr.colorThree, defaultColor))
        binding.frameLayoutExampleDesign.findViewById<TextView>(R.id.comp3_6).setTextColor(toColor(colorStr.colorThree, defaultColor))
        binding.frameLayoutExampleDesign.findViewById<View>(R.id.comp3_7).setBackgroundColor(toColor(colorStr.colorThree, defaultColor))
        binding.frameLayoutExampleDesign.findViewById<TextView>(R.id.comp3_8).setTextColor(toColor(colorStr.colorThree, defaultColor))
        binding.frameLayoutExampleDesign.findViewById<View>(R.id.comp3_9).setBackgroundColor(toColor(colorStr.colorThree, defaultColor))
        binding.frameLayoutExampleDesign.findViewById<ConstraintLayout>(R.id.l_example_design).setBackgroundColor(toColor(colorStr.colorThree, defaultColor))
        binding.frameLayoutExampleDesign.findViewById<CircleImageView>(R.id.comp4).borderColor = toColor(colorStr.colorFour, defaultColor)
    }
}