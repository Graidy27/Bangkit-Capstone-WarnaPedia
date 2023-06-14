package com.dicoding.warnapedia.ui.favorite

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.warnapedia.R
import com.dicoding.warnapedia.data.ColorPalette
import com.dicoding.warnapedia.databinding.FragmentFavoriteBinding
import com.dicoding.warnapedia.helper.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import de.hdodenhof.circleimageview.CircleImageView

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

        if(activity is AppCompatActivity){
            val mainActivity = (activity as? AppCompatActivity)
            val navView = mainActivity?.findViewById<BottomNavigationView>(R.id.nav_view)
            if (navView?.visibility == View.GONE) navView?.visibility = View.VISIBLE
        }

        favoriteViewModel.listColorPalette.observe(viewLifecycleOwner) { listColorPalette ->
            adapter.updateData(listColorPalette)
            adapter.notifyDataSetChanged()
            if (listColorPalette.isNotEmpty()){
                favoriteViewModel.setCurrentColorPalette(listColorPalette[0])
            }
        }
        favoriteViewModel.loadFavoriteColorPalette(viewLifecycleOwner)

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClick(data: ColorPalette) {
                favoriteViewModel.setCurrentColorPalette(data)
            }
        })

        favoriteViewModel.setCurrentDesign(1)

        favoriteViewModel.currentDesign.observe(viewLifecycleOwner)  { currentDesign ->
            when(currentDesign) {
                1 -> { loadWebDesign1() }
                2 -> { loadWebDesign2() }
            }
            setExampleDesignColor(favoriteViewModel.getCurrentColorPalette())
        }

        favoriteViewModel.currentColorPalette.observe(viewLifecycleOwner) { colorPalette ->
            setExampleDesignColor(colorPalette)
        }

        binding.bLeftExampleLayout.setOnClickListener{
            when(favoriteViewModel.getCurrentDesign()) {
                1 -> { favoriteViewModel.setCurrentDesign(2) }
                2 -> { favoriteViewModel.setCurrentDesign(1) }
            }
        }

        binding.bRightExampleLayout.setOnClickListener{
            when(favoriteViewModel.getCurrentDesign()) {
                1 -> { favoriteViewModel.setCurrentDesign(2) }
                2 -> { favoriteViewModel.setCurrentDesign(1) }
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadWebDesign1() {
        binding.frameLayoutExampleDesign.removeAllViews()
        layoutInflater.inflate(R.layout.example_web_design_1, binding.frameLayoutExampleDesign)
    }

    private fun loadWebDesign2() {
        binding.frameLayoutExampleDesign.removeAllViews()
        layoutInflater.inflate(R.layout.example_web_design_2, binding.frameLayoutExampleDesign)
    }

    fun toColor(color: String, defColor: Int) : Int{
        if (color.isNullOrEmpty()) return defColor
        return Color.parseColor(color)
    }

    fun setExampleDesignColor(colorStr: ColorPalette){
        when(favoriteViewModel.getCurrentDesign()){
            1 -> setExampleDesign1Color(colorStr)
            2 -> setExampleDesign2Color(colorStr)
        }
    }
    fun setExampleDesign1Color(colorStr: ColorPalette){
        val defaultColor = ContextCompat.getColor(requireContext(), R.color.F5F5F5)
        ViewCompat.setBackgroundTintList(binding.frameLayoutExampleDesign.findViewById(R.id.comp1_1), ColorStateList.valueOf(toColor(colorStr.color1, defaultColor)))
        ViewCompat.setBackgroundTintList(binding.frameLayoutExampleDesign.findViewById(R.id.comp1_2), ColorStateList.valueOf(toColor(colorStr.color1, defaultColor)))
        ViewCompat.setBackgroundTintList(binding.frameLayoutExampleDesign.findViewById(R.id.comp1_3), ColorStateList.valueOf(toColor(colorStr.color1, defaultColor)))
        ViewCompat.setBackgroundTintList(binding.frameLayoutExampleDesign.findViewById(R.id.comp1_4), ColorStateList.valueOf(toColor(colorStr.color1, defaultColor)))
        binding.frameLayoutExampleDesign.findViewById<TextView>(R.id.comp1_5).setTextColor(toColor(colorStr.color1, defaultColor))
        ViewCompat.setBackgroundTintList(binding.frameLayoutExampleDesign.findViewById(R.id.comp1_6), ColorStateList.valueOf(toColor(colorStr.color1, defaultColor)))
        ViewCompat.setBackgroundTintList(binding.frameLayoutExampleDesign.findViewById(R.id.comp1_7), ColorStateList.valueOf(toColor(colorStr.color1, defaultColor)))
        binding.frameLayoutExampleDesign.findViewById<TextView>(R.id.comp1_8).setTextColor(toColor(colorStr.color1, defaultColor))
        binding.frameLayoutExampleDesign.findViewById<TextView>(R.id.comp2).setTextColor(toColor(colorStr.color2, defaultColor))
        ViewCompat.setBackgroundTintList(binding.frameLayoutExampleDesign.findViewById(R.id.comp3), ColorStateList.valueOf(toColor(colorStr.color3, defaultColor)))
        binding.frameLayoutExampleDesign.findViewById<ConstraintLayout>(R.id.l_example_design).setBackgroundColor(toColor(colorStr.color3, defaultColor))
        ViewCompat.setBackgroundTintList(binding.frameLayoutExampleDesign.findViewById(R.id.comp4), ColorStateList.valueOf(toColor(colorStr.color4, defaultColor)))
    }

    fun setExampleDesign2Color(colorStr: ColorPalette){
        val defaultColor = ContextCompat.getColor(requireContext(), R.color.F5F5F5)
        binding.frameLayoutExampleDesign.findViewById<View>(R.id.comp1).setBackgroundColor(toColor(colorStr.color1, defaultColor))
        binding.frameLayoutExampleDesign.findViewById<TextView>(R.id.comp2_1).setTextColor(toColor(colorStr.color2, defaultColor))
        binding.frameLayoutExampleDesign.findViewById<TextView>(R.id.comp2_2).setTextColor(toColor(colorStr.color2, defaultColor))
        binding.frameLayoutExampleDesign.findViewById<TextView>(R.id.comp3_1).setTextColor(toColor(colorStr.color3, defaultColor))
        binding.frameLayoutExampleDesign.findViewById<TextView>(R.id.comp3_2).setTextColor(toColor(colorStr.color3, defaultColor))
        binding.frameLayoutExampleDesign.findViewById<TextView>(R.id.comp3_3).setTextColor(toColor(colorStr.color3, defaultColor))
        binding.frameLayoutExampleDesign.findViewById<TextView>(R.id.comp3_4).setTextColor(toColor(colorStr.color3, defaultColor))
        binding.frameLayoutExampleDesign.findViewById<View>(R.id.comp3_5).setBackgroundColor(toColor(colorStr.color3, defaultColor))
        binding.frameLayoutExampleDesign.findViewById<TextView>(R.id.comp3_6).setTextColor(toColor(colorStr.color3, defaultColor))
        binding.frameLayoutExampleDesign.findViewById<View>(R.id.comp3_7).setBackgroundColor(toColor(colorStr.color3, defaultColor))
        binding.frameLayoutExampleDesign.findViewById<TextView>(R.id.comp3_8).setTextColor(toColor(colorStr.color3, defaultColor))
        binding.frameLayoutExampleDesign.findViewById<View>(R.id.comp3_9).setBackgroundColor(toColor(colorStr.color3, defaultColor))
        binding.frameLayoutExampleDesign.findViewById<ConstraintLayout>(R.id.l_example_design).setBackgroundColor(toColor(colorStr.color3, defaultColor))
        binding.frameLayoutExampleDesign.findViewById<CircleImageView>(R.id.comp4).borderColor = toColor(colorStr.color4, defaultColor)
    }
}