package com.dicoding.warnapedia.ui.recomendation

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
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
import com.dicoding.warnapedia.databinding.FragmentRecomendationBinding
import com.dicoding.warnapedia.helper.ViewModelFactory
import com.dicoding.warnapedia.ui.detail.DetailFragmentArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import kotlin.math.roundToInt

class RecomendationFragment : Fragment() {

    private lateinit var adapter: RecomendationAdapter

    private var _binding: FragmentRecomendationBinding? = null

    private val binding get() = _binding!!

    private val recomendationViewModel by activityViewModels<RecomendationViewModel>{
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecomendationBinding.inflate(inflater, container, false)
        adapter = RecomendationAdapter(listOf(), requireActivity())
        binding.rvColorPalette.adapter = adapter
        return binding.root
    }

    @SuppressLint("Range")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bLeftExampleLayout.setOnClickListener{
            Log.d("BUTTON LEFT", "test")
        }
        binding.bRightExampleLayout.setOnClickListener{
            Log.d("BUTTON RIGHT", "test")
        }

        val typedValue = TypedValue()
        if (requireContext().theme.resolveAttribute(android.R.attr.actionBarSize, typedValue, true)) {
            val actionBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data, resources.displayMetrics)
            val actionBarHeightInDp = actionBarHeight / resources.displayMetrics.density
            val layoutParams = binding.rvColorPalette.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.topMargin = actionBarHeightInDp.toInt().dp +
                    requireContext().resources.getDimension(R.dimen.example_design_container_height).toInt()
            binding.rvColorPalette.layoutParams = layoutParams
        }

        recomendationViewModel.listColorPalette.observe(viewLifecycleOwner) { listColorPalette ->
            adapter.updateData(listColorPalette)
            adapter.notifyDataSetChanged()
            if (listColorPalette.isNotEmpty()){
                setExampleDesignColor(listColorPalette[0])
            }
        }

        val color_palette = RecomendationFragmentArgs.fromBundle(arguments as Bundle)?.colorPalette
        recomendationViewModel.loadColorPalette(ArrayList(color_palette?.toList()))

        val layoutManager = LinearLayoutManager(activity)
        binding.rvColorPalette.layoutManager = layoutManager
        binding.rvColorPalette.setHasFixedSize(true)

        adapter.setOnItemClickCallback(object : RecomendationAdapter.OnItemClickCallback {
            override fun onItemClick(data: ColorPalette) {
                setExampleDesignColor(data)
            }
        })

        adapter.setOnFavoriteButtonCallback(object : RecomendationAdapter.OnFavoriteButtonClickCallback {
            override fun onFavoriteButtonClick(colorPalette: ColorPalette, button: MaterialButton) {
                button.addOnCheckedChangeListener{ button, isChecked ->
                    if (isChecked){
                        button.icon = ContextCompat.getDrawable(requireContext(), R.drawable.baseline_favorite_24)
                        val data = FavoriteColorPalette()
                        data.let{
                            it.colorPaletteName = colorPalette.colorPaletteName
                            it.colorOne = colorPalette.colorOne
                            it.colorTwo = colorPalette.colorTwo
                            it.colorThree = colorPalette.colorThree
                            it.colorFour = colorPalette.colorFour
                        }
                        recomendationViewModel.insertFavorite(data)
                    } else {
                        button.icon = ContextCompat.getDrawable(requireContext(), R.drawable.baseline_favorite_border_24)
                        val data = FavoriteColorPalette()
                        data.colorPaletteName = colorPalette.colorPaletteName
                        recomendationViewModel.deleteFavorite(data)
                    }
                }
            }
        })

        if(activity is AppCompatActivity){
            val mainActivity = (activity as? AppCompatActivity)
            mainActivity?.setSupportActionBar(binding.toolbar)
            mainActivity?.supportActionBar?.title = "Palette Recomendation"
            mainActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            val navView = mainActivity?.findViewById<BottomNavigationView>(R.id.nav_view)
            navView?.visibility = View.GONE
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

    val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()

    val Float.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}