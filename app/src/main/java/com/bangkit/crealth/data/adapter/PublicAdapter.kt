package com.bangkit.crealth.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.crealth.R
import com.bangkit.crealth.data.model.PublicModel
import com.bangkit.crealth.databinding.ItemPostBinding
import com.bumptech.glide.Glide

class PublicAdapter(private var publicList: List<PublicModel>) :
    RecyclerView.Adapter<PublicAdapter.PublicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PublicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PublicViewHolder, position: Int) {
        holder.bind(publicList[position])
    }

    override fun getItemCount(): Int = publicList.size

    fun updateData(newPublicList: List<PublicModel>) {
        publicList = newPublicList
        notifyDataSetChanged()
    }

    inner class PublicViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(publicModel: PublicModel) {
            binding.nameTextView.text = publicModel.name
            binding.textPostTextView.text = publicModel.textPost
            Glide.with(binding.root.context)
                .load(R.drawable.ic_default_user)
                .into(binding.imageView)
        }
    }
}