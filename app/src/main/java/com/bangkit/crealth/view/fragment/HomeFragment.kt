package com.bangkit.crealth.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.crealth.data.adapter.ArticleHomeAdapter
import com.bangkit.crealth.data.api.NutritionixApi
import com.bangkit.crealth.data.factory.ViewModelFactory
import com.bangkit.crealth.data.response.BrandedFood
import com.bangkit.crealth.data.response.Photo
import com.bangkit.crealth.data.viewmodel.HomeViewModel
import com.bangkit.crealth.databinding.FragmentHomeBinding
import com.bangkit.crealth.view.ChatbotActivity
import com.bangkit.crealth.view.LandingActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null

    private var successMessage: String? = null

    private val binding get() = _binding!!

    private var foodList = mutableListOf<BrandedFood>()

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

//        viewModel.getSession().observe(viewLifecycleOwner) { user ->
//            if (!user.isLogin && user.token.isBlank()) {
//                startActivity(Intent(requireContext(), LandingActivity::class.java))
//            } else {
//                binding.tvName.text = user.name

                successMessage = activity?.intent?.getStringExtra("successMessage")
                if (successMessage != null) {
                    showToast(successMessage!!)
                    activity?.intent?.removeExtra("successMessage")
                }
                binding.btnChatbot.setOnClickListener {
                    startActivity(Intent(requireContext(), ChatbotActivity::class.java))
                }

                fetchData()
            }
//        }
//    }

    private fun fetchData() {
        binding.progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = NutritionixApi.service.searchFood("drugs")
                withContext(Dispatchers.Main) {
                    if (response.branded.isNotEmpty()) {
                        foodList.clear()
                        foodList.addAll(response.branded.map { brandedFood ->
                            BrandedFood(
                                foodName = brandedFood.foodName,
                                servingQty = brandedFood.servingQty,
                                servingUnit = brandedFood.servingUnit,
                                calories = brandedFood.calories,
                                photo = brandedFood.photo,
                                brandName = brandedFood.brandName,
                                brandNameItemName = brandedFood.brandNameItemName,
                                locale = brandedFood.locale,
                                nixItemId = brandedFood.nixItemId
                            )
                        })

                        val adapter = ArticleHomeAdapter(foodList)
                        binding.rvArticleHome.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                        binding.rvArticleHome.adapter = adapter
                    } else {
                        showDummyData()
                        showToast("Error Unknown!")
                    }
                    binding.progressBar.visibility = View.GONE
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showDummyData()
                    showToast("Error Unknown! activate your internet!")
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun showDummyData() {
        val dummyFoodList = listOf(
            BrandedFood(
                foodName = "Dummy Food 1",
                servingQty = "1".toDouble(),
                servingUnit = "serving",
                calories = 100,
                photo = Photo(""),
                brandName = "",
                brandNameItemName = "Dummy Item 1",
                locale = "",
                nixItemId = ""
            ),
            BrandedFood(
                foodName = "Dummy Food 2",
                servingQty = "1".toDouble(),
                servingUnit = "serving",
                calories = 100,
                photo = Photo(""),
                brandName = "",
                brandNameItemName = "Dummy Item 2",
                locale = "",
                nixItemId = ""
            ),
            BrandedFood(
                foodName = "Dummy Food 3",
                servingQty = "1".toDouble(),
                servingUnit = "serving",
                calories = 100,
                photo = Photo(""),
                brandName = "",
                brandNameItemName = "Dummy Item 3",
                locale = "",
                nixItemId = ""
            ),
            BrandedFood(
                foodName = "Dummy Food 4",
                servingQty = "1".toDouble(),
                servingUnit = "serving",
                calories = 100,
                photo = Photo(""),
                brandName = "",
                brandNameItemName = "Dummy Item 4",
                locale = "",
                nixItemId = ""
            )
        )

        val adapter = ArticleHomeAdapter(dummyFoodList)
        binding.rvArticleHome.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvArticleHome.adapter = adapter
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
