package com.dicoding.warnapedia.ui.detail

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.warnapedia.databinding.ItemRowColorBinding
class DetailAdapter(
    private var listColor: Array<String>
) : RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowColorBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        ViewCompat.setBackgroundTintList(viewHolder.itemView, ColorStateList.valueOf(Color.parseColor(listColor[position])))
        viewHolder.binding.tvColorName.text = listColor[position]
    }

    override fun getItemCount(): Int = listColor.size

    class ViewHolder(var binding: ItemRowColorBinding) : RecyclerView.ViewHolder(binding.root)
}