package com.bangkit.crealth.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.crealth.R
import com.bangkit.crealth.data.model.Post
import com.bangkit.crealth.databinding.FragmentPostBinding
import com.bangkit.crealth.data.adapter.PostAdapter

class PostFragment : Fragment() {
    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val dummyPosts = listOf(
            Post(R.drawable.ic_place_holder, "User 1", "This is the first post."),
            Post(R.drawable.ic_place_holder, "User 2", "This is the second post."),
            Post(R.drawable.ic_place_holder, "User 3", "This is the third post."),
            Post(R.drawable.ic_place_holder, "User 1", "This is the first post."),
            Post(R.drawable.ic_place_holder, "User 2", "This is the second post."),
            Post(R.drawable.ic_place_holder, "User 3", "This is the third post.")
        )

        val adapter = PostAdapter(dummyPosts)
        binding.rvPost.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPost.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}