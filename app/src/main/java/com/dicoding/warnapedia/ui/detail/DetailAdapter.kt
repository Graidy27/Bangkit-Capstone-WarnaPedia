package com.dicoding.warnapedia.ui.detail

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.warnapedia.R
import com.dicoding.warnapedia.databinding.ItemRowColorBinding
import com.google.android.material.snackbar.Snackbar
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
        val mToast = Toast.makeText(viewHolder.itemView.context,  viewHolder.itemView.context.getString(R.string.color_copied_to_clipboard, listColor[position]), Toast.LENGTH_LONG)
        viewHolder.itemView.setOnLongClickListener { view ->
            val clipboard = view.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(view.context.resources.getString(R.string.copied_color, listColor[position]), listColor[position])
            clipboard.setPrimaryClip(clip)
            mDisplayToast(mToast, 1000L)
            true
        }
    }

    override fun getItemCount(): Int = listColor.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder(var binding: ItemRowColorBinding) : RecyclerView.ViewHolder(binding.root)

    private fun mDisplayToast(toast: Toast, duration: Long){
        Thread{
            toast.show()
            Thread.sleep(duration)
            toast.cancel()
        }.start()
    }

    private val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()
}