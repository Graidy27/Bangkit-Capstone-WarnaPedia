package com.dicoding.warnapedia.ui.recomendation

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
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
import com.dicoding.warnapedia.data.localdatabase.FavoriteColorPalette
import com.dicoding.warnapedia.databinding.FragmentRecomendationBinding
import com.dicoding.warnapedia.helper.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import de.hdodenhof.circleimageview.CircleImageView
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

        val typedValue = TypedValue()
        if (requireContext().theme.resolveAttribute(android.R.attr.actionBarSize, typedValue, true)) {
            val actionBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data, resources.displayMetrics)
            val actionBarHeightInDp = actionBarHeight / resources.displayMetrics.density
            val layoutParams = binding.rvColorPalette.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.topMargin = actionBarHeightInDp.toInt().dp +
                    requireContext().resources.getDimension(R.dimen.example_design_container_height).toInt()
            binding.rvColorPalette.layoutParams = layoutParams
        }

        if(activity is AppCompatActivity){
            val mainActivity = (activity as? AppCompatActivity)
            mainActivity?.setSupportActionBar(binding.toolbar)
            mainActivity?.supportActionBar?.title = "Palette Recomendation"
            mainActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            val navView = mainActivity?.findViewById<BottomNavigationView>(R.id.nav_view)
            navView?.visibility = View.GONE
        }

        recomendationViewModel.listColorPalette.observe(viewLifecycleOwner) { listColorPalette ->
            adapter.updateData(listColorPalette)
            adapter.notifyDataSetChanged()
            if (listColorPalette.isNotEmpty()){
                recomendationViewModel.setCurrentColorPalette(listColorPalette[0])
            }
        }

        val color_palette = RecomendationFragmentArgs.fromBundle(arguments as Bundle)?.colorPalette
        recomendationViewModel.loadColorPalette(ArrayList(color_palette?.toList()))

        val layoutManager = LinearLayoutManager(activity)
        binding.rvColorPalette.layoutManager = layoutManager
        binding.rvColorPalette.setHasFixedSize(true)

        adapter.setOnItemClickCallback(object : RecomendationAdapter.OnItemClickCallback {
            override fun onItemClick(data: ColorPalette) {
                recomendationViewModel.setCurrentColorPalette(data)
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

        recomendationViewModel.setCurrentDesign(1)

        recomendationViewModel.currentDesign.observe(viewLifecycleOwner)  { currentDesign ->
            when(currentDesign) {
                1 -> { loadWebDesign1() }
                2 -> { loadWebDesign2() }
            }
            setExampleDesignColor(recomendationViewModel.getCurrentColorPalette())
        }

        recomendationViewModel.currentColorPalette.observe(viewLifecycleOwner) { colorPalette ->
            setExampleDesignColor(colorPalette)
        }

        binding.bLeftExampleLayout.setOnClickListener{
            when(recomendationViewModel.getCurrentDesign()) {
                1 -> { recomendationViewModel.setCurrentDesign(2) }
                2 -> { recomendationViewModel.setCurrentDesign(1) }
            }
        }

        binding.bRightExampleLayout.setOnClickListener{
            when(recomendationViewModel.getCurrentDesign()) {
                1 -> { recomendationViewModel.setCurrentDesign(2) }
                2 -> { recomendationViewModel.setCurrentDesign(1) }
            }
        }
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
        when(recomendationViewModel.getCurrentDesign()){
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