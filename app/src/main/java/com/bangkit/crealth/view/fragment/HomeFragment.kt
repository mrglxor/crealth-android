package com.bangkit.crealth.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bangkit.crealth.data.factory.ViewModelFactory
import com.bangkit.crealth.data.viewmodel.HomeViewModel
import com.bangkit.crealth.databinding.FragmentHomeBinding
import com.bangkit.crealth.view.ChatbotActivity
import com.bangkit.crealth.view.LandingActivity

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSession().observe(viewLifecycleOwner) {user ->
            if (!user.isLogin && user.token.isBlank()) {
                startActivity(Intent(requireContext(), LandingActivity::class.java))
            } else {
                binding.tvName.text = user.name

                val successMessage = activity?.intent?.getStringExtra("successMessage")
                if (successMessage != null) {
                    Toast.makeText(requireContext(), successMessage, Toast.LENGTH_SHORT).show()
                    activity?.intent?.removeExtra("successMessage")
                }

                binding.btnNotif.setOnClickListener {
                    viewModel.logout()
                }
                binding.btnChatbot.setOnClickListener {
                    startActivity(Intent(requireContext(), ChatbotActivity::class.java))
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}