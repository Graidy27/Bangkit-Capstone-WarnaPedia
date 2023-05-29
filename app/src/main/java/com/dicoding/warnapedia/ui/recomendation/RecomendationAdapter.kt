package com.dicoding.warnapedia.ui.recomendation

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.warnapedia.data.ColorPalette
import com.dicoding.warnapedia.databinding.ItemRowColorPaletteBinding
import com.dicoding.warnapedia.ui.recomendation.RecomendationFragmentDirections
import com.google.android.material.button.MaterialButton

class RecomendationAdapter(
    private var listColorPalette: List<ColorPalette>, private val context: FragmentActivity
) : RecyclerView.Adapter<RecomendationAdapter.ViewHolder>() {

    private var selectedItemPosition: Int = 0

    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    interface OnItemClickCallback {
        fun onItemClick(data: ColorPalette)
    }
    private lateinit var onFavoriteButtonClickCallback: OnFavoriteButtonClickCallback
    fun setOnFavoriteButtonCallback(onFavoriteButtonClickCallback: OnFavoriteButtonClickCallback) {
        this.onFavoriteButtonClickCallback = onFavoriteButtonClickCallback
    }
    interface OnFavoriteButtonClickCallback {
        fun onFavoriteButtonClick(data: ColorPalette, button: MaterialButton)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowColorPaletteBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val (color_palette_name, color_one, color_two, color_three, color_four) = listColorPalette[position]

        viewHolder.binding.tvColorPaletteName.text = color_palette_name
        viewHolder.binding.clBackgroundLayout.setBackgroundColor(Color.parseColor(color_one))
        viewHolder.binding.colorTwo.setBackgroundColor(Color.parseColor(color_two))
        viewHolder.binding.colorThree.setBackgroundColor(Color.parseColor(color_three))
        viewHolder.binding.colorFour.setBackgroundColor(Color.parseColor(color_four))

        if (selectedItemPosition == position){
            viewHolder.binding.llSelectedColorPalette.visibility = View.VISIBLE
        }else{
            viewHolder.binding.llSelectedColorPalette.visibility = View.GONE
        }

        onFavoriteButtonClickCallback.onFavoriteButtonClick(listColorPalette[viewHolder.adapterPosition], viewHolder.binding.mbFavoriteButton)

        viewHolder.itemView.setOnClickListener {
            onItemClickCallback.onItemClick(listColorPalette[viewHolder.adapterPosition])
            setSingleSelection(viewHolder.adapterPosition)
        }

        viewHolder.binding.mbViewDetailButton.setOnClickListener {
            val toDetailFragment = RecomendationFragmentDirections.actionNavigationRecomendationToDetailFragment(
                arrayOf(
                    color_one, color_two, color_three, color_four)
            )
            toDetailFragment.colorPaletteName = color_palette_name
            viewHolder.itemView.findNavController().navigate(toDetailFragment)
        }
    }

    override fun getItemCount(): Int = listColorPalette.size

    class ViewHolder(var binding: ItemRowColorPaletteBinding) : RecyclerView.ViewHolder(binding.root)

    private fun setSingleSelection(adapterPosition: Int){
        if(adapterPosition == RecyclerView.NO_POSITION) return

        notifyItemChanged(selectedItemPosition)

        selectedItemPosition =  adapterPosition

        notifyItemChanged(selectedItemPosition)
    }

    fun updateData(newData: List<ColorPalette>) {
        listColorPalette = newData
        notifyDataSetChanged()
    }
}