package com.bangkit.crealth.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.crealth.data.model.Option
import com.bangkit.crealth.databinding.CheckboxContainerBinding

class ViewPagerAdapter(
    private var options: List<Option>,
    private val onCheckedChange: (Option, Boolean) -> Unit
) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(private val binding: CheckboxContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(options: List<Option>) {
            val adapter = SymptomsAdapter(options, onCheckedChange)
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                this.adapter = adapter
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val binding = CheckboxContainerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val start = position * 10
        val end = minOf(start + 10, options.size)
        val sublist = options.subList(start, end)
        holder.bind(sublist)
    }

    override fun getItemCount(): Int {
        return (options.size + 9) / 10
    }

    fun updateOptions(newOptions: List<Option>) {
        options = newOptions
        notifyDataSetChanged()
    }
}
