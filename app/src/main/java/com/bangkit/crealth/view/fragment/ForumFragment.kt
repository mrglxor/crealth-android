package com.bangkit.crealth.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bangkit.crealth.data.adapter.ForumPagerAdapter
import com.bangkit.crealth.databinding.FragmentForumBinding
import com.google.android.material.tabs.TabLayoutMediator

class ForumFragment : Fragment() {
    private var _binding: FragmentForumBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val successMessage = arguments?.getString("successMessage")
        successMessage?.let {
            showToast(it)
        }

        binding.btnNotif.setOnClickListener {
            Toast.makeText(requireContext(), "Notification is under Maintenance", Toast.LENGTH_LONG).show()
            binding.btnNotif.isEnabled = false
        }

        val adapter = ForumPagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Public Post"
                1 -> "Create"
                else -> null
            }
        }.attach()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
