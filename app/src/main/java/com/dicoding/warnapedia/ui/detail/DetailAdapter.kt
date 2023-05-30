package com.dicoding.warnapedia.ui.detail

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.warnapedia.databinding.ItemRowColorBinding
import kotlin.math.roundToInt

class DetailAdapter(
    private var listColor: Array<String>
) : RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowColorBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (position == 0){
            val itemLayoutParams = viewHolder.itemView.layoutParams as ViewGroup.MarginLayoutParams
            itemLayoutParams.topMargin = 10.dp
            viewHolder.itemView.layoutParams = itemLayoutParams
        }
        ViewCompat.setBackgroundTintList(viewHolder.itemView, ColorStateList.valueOf(Color.parseColor(listColor[position])))
        viewHolder.binding.tvColorName.text = listColor[position]
    }

    override fun getItemCount(): Int = listColor.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder(var binding: ItemRowColorBinding) : RecyclerView.ViewHolder(binding.root)

    val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()
}