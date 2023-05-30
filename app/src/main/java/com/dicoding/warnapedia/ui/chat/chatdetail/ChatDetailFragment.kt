package com.dicoding.warnapedia.ui.chat.chatdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.warnapedia.R
import com.dicoding.warnapedia.data.Chat
import com.dicoding.warnapedia.data.ExampleChatData
import com.dicoding.warnapedia.databinding.FragmentChatDetailBinding
import com.dicoding.warnapedia.helper.ViewModelFactory
import com.dicoding.warnapedia.ui.recomendation.RecomendationAdapter
import com.dicoding.warnapedia.ui.recomendation.RecomendationFragmentArgs
import com.google.android.material.bottomnavigation.BottomNavigationView

class ChatDetailFragment : Fragment() {

    private lateinit var adapter: ChatDetailAdapter

    private var _binding: FragmentChatDetailBinding? = null

    private val binding get() = _binding!!

    private val chatDetailViewModel by activityViewModels<ChatDetailViewModel>{
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatDetailBinding.inflate(inflater, container, false)
        adapter = ChatDetailAdapter(listOf())
        binding.rvChat.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(activity is AppCompatActivity){
            val mainActivity = (activity as? AppCompatActivity)
            mainActivity?.setSupportActionBar(binding.toolbar)
            mainActivity?.supportActionBar?.title = "Warna Pedia"
            mainActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            val navView = mainActivity?.findViewById<BottomNavigationView>(R.id.nav_view)
            navView?.visibility = View.GONE
        }

        chatDetailViewModel.listChat.observe(viewLifecycleOwner) { listChat ->
            adapter.updateData(listChat)
            adapter.notifyDataSetChanged()
        }

        chatDetailViewModel.loadChat(ExampleChatData.listData)

        val layoutManager = LinearLayoutManager(activity)
        binding.rvChat.layoutManager = layoutManager
        binding.rvChat.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}