package com.dicoding.warnapedia.ui.chat.chatdetail

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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

        chatDetailViewModel.listChat.observe(viewLifecycleOwner) { listChat ->
            adapter.updateData(listChat)
        }

        chatDetailViewModel.loadChat(viewLifecycleOwner, requireActivity())

        binding.btnSend.setOnClickListener {
            val text = binding.textMessage.text.toString()
            if (!text.isNullOrEmpty()){
                chatDetailViewModel.addChat(text)
                binding.textMessage.setText("")
                chatDetailViewModel.getResponse(text)
                binding.rvChat.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        inflater.inflate(R.menu.chat_detail_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete -> {
                val dialogBuilder = AlertDialog.Builder(requireActivity(), R.style.CustomAlertDialog)
                dialogBuilder.setMessage(resources.getString(R.string.delete_confirmation))
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