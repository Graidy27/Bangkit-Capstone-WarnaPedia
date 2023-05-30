package com.dicoding.warnapedia.ui.chat.chatdetail

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.warnapedia.data.Chat
import com.dicoding.warnapedia.data.ColorPalette
import com.dicoding.warnapedia.databinding.ItemRowChatBinding
import com.dicoding.warnapedia.databinding.ItemRowColorBinding
import com.dicoding.warnapedia.ui.favorite.FavoriteFragmentDirections

class ChatDetailAdapter(
    private var listChat: List<Chat>
) : RecyclerView.Adapter<ChatDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowChatBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val (type, message, colorPalette) = listChat[position]
        viewHolder.binding.tvMessage.text = message
        if (type == 1){
            viewHolder.binding.goToDesign.visibility = VISIBLE
            viewHolder.binding.goToDesign.setOnClickListener{
                val toRecomendationFragment = ChatDetailFragmentDirections.actionChatDetailFragmentToNavigationRecomendation(
                    colorPalette?.toTypedArray()
                )
                viewHolder.itemView.findNavController().navigate(toRecomendationFragment)
            }
        }
    }

    override fun getItemCount(): Int = listChat.size

    class ViewHolder(var binding: ItemRowChatBinding) : RecyclerView.ViewHolder(binding.root)

    fun updateData(newData: List<Chat>) {
        listChat = newData
        notifyDataSetChanged()
    }
}