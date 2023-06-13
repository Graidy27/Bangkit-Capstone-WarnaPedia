package com.dicoding.warnapedia.ui.favorite

import android.content.res.Resources
import android.graphics.Color
import android.util.Log
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.warnapedia.R
import com.dicoding.warnapedia.data.ColorPalette
import com.dicoding.warnapedia.databinding.ItemRowColorPaletteBinding
import com.dicoding.warnapedia.repository.FavoriteColorPaletteRepository
import com.google.android.material.button.MaterialButton
import kotlin.math.roundToInt

class FavoriteAdapter(
    private var listColorPalette: List<ColorPalette>, private val context: FragmentActivity
) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private var selectedItemPosition: Int = 0
    private val mFavoriteColorPaletteRepository: FavoriteColorPaletteRepository = FavoriteColorPaletteRepository(context)

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
        if (position == 0){
            val itemLayoutParams = viewHolder.itemView.layoutParams as ViewGroup.MarginLayoutParams
            itemLayoutParams.topMargin = 10.dp
            viewHolder.itemView.layoutParams = itemLayoutParams
        }
        val (id, color_palette_name, color_one, color_two, color_three, color_four) = listColorPalette[position]
        Log.d("ID", id.toString())
        viewHolder.binding.tvColorPaletteName.text = color_palette_name
        viewHolder.binding.clBackgroundLayout.setBackgroundColor(Color.parseColor(color_one))
        viewHolder.binding.colorTwo.setBackgroundColor(Color.parseColor(color_two))
        viewHolder.binding.colorThree.setBackgroundColor(Color.parseColor(color_three))
        viewHolder.binding.colorFour.setBackgroundColor(Color.parseColor(color_four))
        if (selectedItemPosition == position){
            viewHolder.binding.llSelectedColorPalette.visibility = VISIBLE
        }else{
            viewHolder.binding.llSelectedColorPalette.visibility = GONE
        }
        viewHolder.binding.mbFavoriteButton.visibility = GONE
        val layoutParams = viewHolder.binding.mbViewDetailButton.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.setMargins(0, 0, 0, 0)
        viewHolder.binding.mbViewDetailButton.layoutParams = layoutParams
        viewHolder.itemView.setOnClickListener {
            if (selectedItemPosition != viewHolder.adapterPosition){
                onItemClickCallback.onItemClick(listColorPalette[viewHolder.adapterPosition])
                setSingleSelection(viewHolder.adapterPosition)
            }
        }
        val gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onLongPress(e: MotionEvent) {
                toDetailFragment(viewHolder, id, color_palette_name, color_one, color_two, color_three, color_four)
            }
        })
        viewHolder.itemView.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            false
        }
        viewHolder.binding.mbViewDetailButton.setOnClickListener {
            toDetailFragment(viewHolder, id, color_palette_name, color_one, color_two, color_three, color_four)
        }
    }

    override fun getItemCount(): Int = listColorPalette.size

    override fun getItemViewType(position: Int): Int {
        return position
    }
    class ViewHolder(var binding: ItemRowColorPaletteBinding) : RecyclerView.ViewHolder(binding.root)

    private fun setSingleSelection(adapterPosition: Int){
        if(adapterPosition == RecyclerView.NO_POSITION) return

        notifyItemChanged(selectedItemPosition)

        selectedItemPosition =  adapterPosition

        notifyItemChanged(selectedItemPosition)
    }

    val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()

    fun updateData(newData: List<ColorPalette>) {
        listColorPalette = newData
        notifyDataSetChanged()
    }

    private fun toDetailFragment(viewHolder: ViewHolder,
                                 id: Int,
                                 color_palette_name: String,
                                 color_one: String,
                                 color_two: String,
                                 color_three: String,
                                 color_four: String,
    ){
        val toDetailFragment = FavoriteFragmentDirections.actionNavigationDashboardToDetailFragment(
            id,
            arrayOf(
                color_one, color_two, color_three, color_four),
            context.resources.getString(R.string.FAVORITE)
        )
        toDetailFragment.colorPaletteName = color_palette_name
        viewHolder.itemView.findNavController().navigate(toDetailFragment)
    }
}