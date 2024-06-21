package com.bangkit.crealth.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.bangkit.crealth.R
import com.bangkit.crealth.data.factory.ForumFactory
import com.bangkit.crealth.data.model.PostModel
import com.bangkit.crealth.data.viewmodel.PostViewModel
import com.bangkit.crealth.databinding.FragmentPostBinding
import com.bangkit.crealth.view.LandingActivity

class PostFragment : Fragment() {
    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<PostViewModel> {
        ForumFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (!user.isLogin && user.token.isBlank()) {
                startActivity(Intent(requireContext(), LandingActivity::class.java))
            } else {

                viewModel.postResult.observe(viewLifecycleOwner) { response ->
                    response?.let {
                        if(it.error == null){
                            childFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                            val fragment = ForumFragment().apply {
                                arguments = Bundle().apply {
                                    putString("successMessage", response.message)
                                }
                            }

                            val transaction = requireActivity().supportFragmentManager.beginTransaction()
                            transaction.replace(R.id.fragment_container, fragment)
                            transaction.addToBackStack(null)
                            transaction.commit()
                        }else{
                            showMessageDialog(response.error!!)
                            binding.shareButton.text =  getString(R.string.error)
                        }
                    }
                }
                binding.userName.text = user.name

                binding.shareButton.setOnClickListener {
                    val post = binding.postText.text.toString()
                    viewModel.createPost(PostModel(user.name,post),requireContext())
                }
            }
        }
    }

    private fun showMessageDialog(responseMessage: String) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Message")
            setIcon(R.mipmap.icon_launcher)
            setMessage(responseMessage)
            setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            create()
            show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}