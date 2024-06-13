package com.bangkit.crealth.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.crealth.R
import com.bangkit.crealth.data.adapter.ArticleAdapter
import com.bangkit.crealth.data.model.Article
import com.bangkit.crealth.databinding.FragmentArticleBinding

class ArticleFragment : Fragment() {
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearch()
    }

    private fun setupSearch() {
        //
    }

    private fun setupRecyclerView() {
        val dummyPosts = listOf(
            Article(1,R.drawable.ic_place_holder, "Title 1", "Sept, 28 2023"),
            Article(2,R.drawable.ic_place_holder, "Title 2", "Sept, 28 2024"),
            Article(3,R.drawable.ic_place_holder, "Title 3", "Sept, 28 2025"),
            Article(4,R.drawable.ic_place_holder, "Title 1", "Sept, 28 2026"),
            Article(5,R.drawable.ic_place_holder, "Title 2", "Sept, 28 2027"),
            Article(6,R.drawable.ic_place_holder, "Title 3", "Sept, 28 2028")
        )

        val adapter = ArticleAdapter(dummyPosts)
        binding.rvArticleList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvArticleList.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
