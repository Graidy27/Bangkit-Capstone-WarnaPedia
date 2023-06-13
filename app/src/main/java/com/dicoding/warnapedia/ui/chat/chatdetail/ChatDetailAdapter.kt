package com.dicoding.warnapedia.ui.chat.chatdetail

import android.os.Handler
import android.view.LayoutInflater
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
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

    var isTypeWriterOn: Boolean = false
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowChatBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val (type, message, colorPalette) = listChat[position]
        if (isTypeWriterOn){
            typeWriterEffect(viewHolder.binding.tvMessage, message, 10L)
            isTypeWriterOn = false
        }else {
            viewHolder.binding.tvMessage.text = message
        }

        if(type == 0) {
            viewHolder.binding.userIcon.setImageResource(R.drawable.baseline_user_icon)
            viewHolder.binding.userIcon.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
            viewHolder.binding.layoutCard.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }else if(type == 1) {
            viewHolder.binding.userIcon.setImageResource(R.drawable.warnapedia_icon_logo)
            viewHolder.binding.userIcon.setBackgroundColor(ContextCompat.getColor(context, R.color.F1C743))
            viewHolder.binding.layoutCard.setBackgroundColor(ContextCompat.getColor(context, R.color.D8D8D8))
        }else if(type == 2){
            viewHolder.binding.userIcon.setBackgroundColor(ContextCompat.getColor(context, R.color.F1C743))
            viewHolder.binding.layoutCard.setBackgroundColor(ContextCompat.getColor(context, R.color.D8D8D8))
            viewHolder.binding.userIcon.setImageResource(R.drawable.warnapedia_icon_logo)
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

    private fun typeWriterEffect(textView: TextView, text: String, delayMillis:Long) {
        var index = 0
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                textView.text = text.substring(0, index++)
                if (index <= text.length) {
                    handler.postDelayed(this, delayMillis)
                }
            }
        }, delayMillis)
    }

    fun updateData(newData: List<Chat>, typeWriterOn: Boolean = false) {
        isTypeWriterOn = typeWriterOn
        listChat = newData
        notifyDataSetChanged()
    }

    fun getDataList(): List<Chat> {
        return listChat
    }
}