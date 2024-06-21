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
import com.bangkit.crealth.data.viewmodel.ProfileViewModel
import com.bangkit.crealth.databinding.FragmentProfileBinding
import com.bangkit.crealth.view.LandingActivity

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNotif.setOnClickListener {
            Toast.makeText(requireContext(), "Notification is under Maintenance", Toast.LENGTH_LONG).show()
            binding.btnNotif.isEnabled = false
        }

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (!user.isLogin && user.token.isBlank()) {
                startActivity(Intent(requireContext(), LandingActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                })
            } else {
                binding.tvUserFullName.text = user.name
                binding.tvUserEmail.text = user.email

                binding.btnLogout.setOnClickListener {
                    viewModel.logout()
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
