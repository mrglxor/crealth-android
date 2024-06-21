package com.bangkit.crealth.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.crealth.data.viewmodel.ArticleViewModel
import com.bangkit.crealth.databinding.FragmentArticleBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.bangkit.crealth.data.adapter.ArticleAdapter
import com.bangkit.crealth.data.factory.ArticleFactory
import com.bangkit.crealth.data.model.Article
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ArticleFragment : Fragment(), ArticleAdapter.OnLoadStateButtonClickListener {

    private var hasPreviousPage = false
    private var hasNextPage = false
    private lateinit var adapter: ArticleAdapter
    private var searchJob: Job? = null

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ArticleViewModel> {
        ArticleFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNotif.setOnClickListener {
            Toast.makeText(requireContext(), "Notification is under Maintenance", Toast.LENGTH_LONG).show()
            binding.btnNotif.isEnabled = false
        }

        setupSearchEditText()
        setupRecyclerView()
        observeArticles()
    }

    private fun setupSearchEditText() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not used
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not used
            }

            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim()
                performSearch(query)
            }
        })
    }

    private fun performSearch(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchArticles(query)
        }
        viewModel.filteredArticles
    }


    private fun setupRecyclerView() {
        adapter = ArticleAdapter()
        adapter.setLoadStateButtonClickListener(this)

        binding.rvArticleList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvArticleList.adapter = adapter

        adapter.addLoadStateListener { combinedLoadStates ->
            binding.progressBar.visibility = if (combinedLoadStates.refresh is LoadState.Loading) View.VISIBLE else View.GONE

            val isListEmpty = combinedLoadStates.refresh is LoadState.NotLoading && adapter.itemCount == 0
            binding.tvEmptyView.visibility = if (isListEmpty || combinedLoadStates.refresh is LoadState.Error) View.VISIBLE else View.GONE

            val errorState = combinedLoadStates.refresh as? LoadState.Error
            errorState?.let {
                Log.d("RC Article", "Server Error ${it.error.localizedMessage}")
                val messageError = "Server Error!"
                binding.tvEmptyView.text = messageError
            }

            binding.btnRetry.visibility = if (combinedLoadStates.refresh is LoadState.Error) View.VISIBLE else View.GONE
            binding.btnRetry.setOnClickListener {
                adapter.retry()
            }

            hasPreviousPage = combinedLoadStates.append is LoadState.NotLoading
            hasNextPage = combinedLoadStates.prepend is LoadState.NotLoading
            adapter.setLoadState(hasPreviousPage, hasNextPage)
        }
        lifecycleScope.launch {
            viewModel.filteredArticles.observe(viewLifecycleOwner) { pagingData: PagingData<Article> ->
                adapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
            }
        }
    }

    private fun observeArticles() {
        lifecycleScope.launch {
            viewModel.articles.observe(viewLifecycleOwner) { pagingData: PagingData<Article> ->
                adapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchJob?.cancel()
        _binding = null
    }

    override fun onPreviousClick() {
        adapter.refresh()
    }

    override fun onNextClick() {
        adapter.refresh()
    }
}
