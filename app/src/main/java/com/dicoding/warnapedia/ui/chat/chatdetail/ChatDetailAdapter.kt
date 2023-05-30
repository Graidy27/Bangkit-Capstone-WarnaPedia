package com.dicoding.warnapedia.ui.chat.chatdetail

import android.util.Log
import android.view.LayoutInflater
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.warnapedia.R
import com.dicoding.warnapedia.data.Chat
import com.dicoding.warnapedia.databinding.ItemRowChatBinding

class ChatDetailAdapter(
    private var listChat: List<Chat>, private val context: FragmentActivity
) : RecyclerView.Adapter<ChatDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowChatBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val (type, message, colorPalette) = listChat[position]
        viewHolder.binding.tvMessage.text = message
        if(type == 0) {
            viewHolder.binding.userIcon.setImageResource(R.drawable.baseline_user_icon)
            viewHolder.binding.layoutCard.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }else if(type == 1) {
            viewHolder.binding.userIcon.setImageResource(R.drawable.ic_launcher_warnapedia_textlogo_vector)
            viewHolder.binding.layoutCard.setBackgroundColor(ContextCompat.getColor(context, R.color.D8D8D8))
        }else if(type == 2){
            viewHolder.binding.userIcon.setImageResource(R.drawable.ic_launcher_warnapedia_textlogo_vector)
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

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder(var binding: ItemRowChatBinding) : RecyclerView.ViewHolder(binding.root)

    fun updateData(newData: List<Chat>) {
        listChat = newData
        notifyDataSetChanged()
    }

    fun getDataList(): List<Chat> {
        return listChat
    }
}