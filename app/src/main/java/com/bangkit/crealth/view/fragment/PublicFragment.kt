package com.bangkit.crealth.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.crealth.R
import com.bangkit.crealth.data.adapter.PublicAdapter
import com.bangkit.crealth.data.factory.ForumFactory
import com.bangkit.crealth.data.model.PublicModel
import com.bangkit.crealth.data.viewmodel.PublicViewModel
import com.bangkit.crealth.databinding.FragmentPublicBinding

class PublicFragment : Fragment() {
    private var _binding: FragmentPublicBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<PublicViewModel> {
        ForumFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPublicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
        viewModel.getPost(requireContext())
        showLoading(true)

        binding.btnRetry.setOnClickListener {
            viewModel.getPost(requireContext())
            showLoading(true)
        }

        binding.searchForumPost.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.searchForumPost.keyListener = null
                Toast.makeText(requireContext(), "Search Public is under Maintenance", Toast.LENGTH_LONG).show()
                binding.searchForumPost.isEnabled = true
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvPost.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPost.adapter = PublicAdapter(emptyList())
    }

    private fun observeViewModel() {
        viewModel.publicResult.observe(viewLifecycleOwner) { response ->
            when {
                response.error != null -> {
                    showLoading(false)
                    binding.tvEmptyView.visibility = View.GONE
                    binding.rvPost.visibility = View.GONE
                    binding.btnRetry.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), response.error, Toast.LENGTH_SHORT).show()
                }
                response.data.isNullOrEmpty() -> {
                    showLoading(false)
                    binding.tvEmptyView.visibility = View.VISIBLE
                    binding.rvPost.visibility = View.GONE
                    binding.btnRetry.visibility = View.GONE
                }
                else -> {
                    val publicModels = response.data.map { post ->
                        PublicModel(
                            R.drawable.ic_place_holder,
                            post.name!!,
                            post.post!!
                        )
                    }
                    (binding.rvPost.adapter as PublicAdapter).updateData(publicModels)
                    binding.tvEmptyView.visibility = View.GONE
                    binding.rvPost.visibility = View.VISIBLE
                    binding.btnRetry.visibility = View.GONE
                    showLoading(false)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
