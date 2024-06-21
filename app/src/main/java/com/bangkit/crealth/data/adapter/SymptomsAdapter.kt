package com.bangkit.crealth.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.crealth.data.model.Option
import com.bangkit.crealth.databinding.ItemCheckboxBinding

class SymptomsAdapter(
    private val options: List<Option>,
    private val onCheckedChange: (Option, Boolean) -> Unit
) : RecyclerView.Adapter<SymptomsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemCheckboxBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(option: Option) {
            binding.checkbox.text = option.text
            binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                onCheckedChange(option, isChecked)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCheckboxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(options[position])
    }

    override fun getItemCount(): Int = options.size
}
