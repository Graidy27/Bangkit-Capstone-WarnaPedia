package com.dicoding.warnapedia.ui.chat.chatdetail

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.warnapedia.databinding.ItemRowChatBinding
import com.dicoding.warnapedia.databinding.ItemRowColorBinding

class ChatDetailAdapter(
    private var listChat: Array<String>
) : RecyclerView.Adapter<ChatDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowChatBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = listChat.size

    class ViewHolder(var binding: ItemRowChatBinding) : RecyclerView.ViewHolder(binding.root)
}