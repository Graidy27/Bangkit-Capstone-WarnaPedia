package com.dicoding.warnapedia.ui.chat.chatdetail

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.warnapedia.R
import com.dicoding.warnapedia.databinding.FragmentChatDetailBinding
import com.dicoding.warnapedia.helper.CheckConnection
import com.dicoding.warnapedia.helper.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class ChatDetailFragment : Fragment() {

    private lateinit var adapter: ChatDetailAdapter

    private var _binding: FragmentChatDetailBinding? = null

    private val binding get() = _binding!!

    private val chatDetailViewModel by activityViewModels<ChatDetailViewModel>{
        ViewModelFactory.getInstance(requireActivity())
    }

    private val checkConnection by lazy {
        getActivity()?.getApplication()
        ?.let { CheckConnection(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatDetailBinding.inflate(inflater, container, false)
        adapter = ChatDetailAdapter(listOf(), requireActivity())
        binding.rvChat.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val layoutManager = LinearLayoutManager(activity)
        binding.rvChat.layoutManager = layoutManager
        binding.rvChat.setHasFixedSize(true)

        if(activity is AppCompatActivity){
            val mainActivity = (activity as? AppCompatActivity)
            mainActivity?.setSupportActionBar(binding.toolbar)
            mainActivity?.supportActionBar?.title = "Warna Pedia"
            mainActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            val navView = mainActivity?.findViewById<BottomNavigationView>(R.id.nav_view)
            navView?.visibility = View.GONE
        }

        checkConnection?.observe(requireActivity()) {
            if (it) {
                (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Online"
            } else {
                (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Offline"
            }
        }

        chatDetailViewModel.listChat.observe(viewLifecycleOwner) { listChat ->
            val isNewData = chatDetailViewModel.getIsNewData()
            if (isNewData){
                adapter.updateData(listChat, true)
                chatDetailViewModel.setIsNewData(false)
            }else{
                adapter.updateData(listChat)
            }
            binding.rvChat.scrollToPosition(adapter.itemCount - 1)
        }

        chatDetailViewModel.loadChat(viewLifecycleOwner)

        chatDetailViewModel.isLoading.observe(viewLifecycleOwner) {
            isLoading ->
            (activity as? AppCompatActivity)?.supportActionBar?.subtitle = if (isLoading == true){
                binding.btnSend.isEnabled = false
                "Warna Pedia is typing..."
            }else {
                binding.btnSend.isEnabled = true
                "Online"
            }
        }

        binding.btnSend.setOnClickListener {
            val text = binding.textMessage.text.toString()
            if (!text.isNullOrEmpty()){
                chatDetailViewModel.addChat(text)
                binding.textMessage.setText("")
                chatDetailViewModel.getResponse(text, viewLifecycleOwner)
            }
        }

        chatDetailViewModel.isLoading.observe(viewLifecycleOwner){ boolean ->

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        inflater.inflate(R.menu.chat_detail_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete -> {
                val dialogBuilder = AlertDialog.Builder(requireActivity(), R.style.CustomAlertDialog)
                dialogBuilder.setMessage(resources.getString(R.string.delete_chat_confirmation))
                    .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                        chatDetailViewModel.deleteChat()
                        dialog.dismiss()
                    })
                    .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                    })
                    .create()
                    .show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}