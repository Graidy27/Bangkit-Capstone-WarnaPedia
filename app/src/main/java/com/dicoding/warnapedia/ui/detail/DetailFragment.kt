package com.dicoding.warnapedia.ui.detail

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.warnapedia.R
import com.dicoding.warnapedia.data.ColorPalette
import com.dicoding.warnapedia.data.localdatabase.FavoriteColorPalette
import com.dicoding.warnapedia.databinding.FragmentDetailBinding
import com.dicoding.warnapedia.helper.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File
import java.io.FileOutputStream


class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null

    private val binding get() = _binding!!

    private val detailViewModel by activityViewModels<DetailViewModel>{
        ViewModelFactory.getInstance(requireActivity())
    }
    private lateinit var dialogBuilder: AlertDialog
    private lateinit var editText: EditText
    private lateinit var colorPalette: ColorPalette

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

        val id = DetailFragmentArgs.fromBundle(arguments as Bundle).id
        val colors = DetailFragmentArgs.fromBundle(arguments as Bundle).colors
        val color_palette_name = DetailFragmentArgs.fromBundle(arguments as Bundle).colorPaletteName
        colorPalette = ColorPalette(id, color_palette_name, colors[0],colors[1],colors[2],colors[3])
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
            setExampleDesignColor(colorPalette)
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

        binding.bShareDesign.setOnClickListener {
            val viewToShare = binding.frameLayoutExampleDesign.findViewById<ConstraintLayout>(R.id.l_example_design)
            detailViewModel.shareColor(viewToShare, colorPalette)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        inflater.inflate(R.menu.detail_menu, menu)
        val favorite = menu.findItem(R.id.favorite)
        favorite.isChecked = true
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_edit_color_palette_name, null)
        editText = dialogView.findViewById(R.id.et_color_palette_name)
        editText.setText(colorPalette.name)
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length?:0 >= detailViewModel.characterMaxLength) {
                    editText.error = resources.getString(R.string.maximum_character, detailViewModel.characterMaxLength.toString())
                } else {
                    editText.error = null
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        dialogBuilder = AlertDialog.Builder(requireActivity(), R.style.CustomAlertDialog)
            .setView(dialogView)
            .setMessage(resources.getString(R.string.edit_color_palette_name))
            .setPositiveButton(resources.getString(R.string.PROCEED)) { dialog, _ ->
                val enteredText = editText.text.toString()
                detailViewModel.updateFavoriteColorPaletteName(enteredText, colorPalette.id).observe(viewLifecycleOwner) { isSuccess ->
                    if (isSuccess) {
                        colorPalette.name = enteredText
                        (activity as? AppCompatActivity)?.supportActionBar?.title = enteredText
                        editText.setText(enteredText)
                    }
                }
                dialog.dismiss()
            }
            .setNegativeButton(resources.getString(R.string.CANCEL)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val curr_favorite_color_palette = FavoriteColorPalette(colorPalette.id)
        return when (item.itemId) {
            R.id.favorite -> {
                if (item.isChecked){
                    detailViewModel.deleteFavorite(curr_favorite_color_palette)
                    item.icon = ContextCompat.getDrawable(requireContext(), R.drawable.baseline_favorite_border_24)
                    item.isChecked = false
                }else {
                    detailViewModel.insertFavorite(curr_favorite_color_palette)
                    item.icon = ContextCompat.getDrawable(requireContext(), R.drawable.baseline_favorite_24)
                    item.isChecked = true
                }
                true
            }
            R.id.edit -> {
                dialogBuilder.show()
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