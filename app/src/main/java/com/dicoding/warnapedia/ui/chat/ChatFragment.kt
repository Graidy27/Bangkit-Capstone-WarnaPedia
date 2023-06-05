package com.dicoding.warnapedia.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.dicoding.warnapedia.R
import com.dicoding.warnapedia.databinding.FragmentChatBinding
import com.dicoding.warnapedia.helper.ViewModelFactory
import com.dicoding.warnapedia.ui.chat.chatdetail.ChatDetailViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null

    private val binding get() = _binding!!

    private val chatViewModel by activityViewModels<ChatDetailViewModel>{
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(activity is AppCompatActivity){
            val mainActivity = (activity as? AppCompatActivity)
            val navView = mainActivity?.findViewById<BottomNavigationView>(R.id.nav_view)
            if (navView?.visibility == GONE) navView?.visibility = View.VISIBLE
        }

        binding.bTypeHere.setOnClickListener {
            val toChatDetailFragment = ChatFragmentDirections.actionNavigationHomeToChatDetailFragment()
            findNavController().navigate(toChatDetailFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}