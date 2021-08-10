package com.example.comparepharma.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.comparepharma.databinding.ItemBinding
import com.example.comparepharma.model.data.MedicineCost

class PharmaAdapter :
    RecyclerView.Adapter<PharmaAdapter.PharmaViewHolder>() {

    private var pharmaData: List<MedicineCost> = listOf()
    private var onItemClickListener: (MedicineCost) -> Unit = {}

    fun setOnItemViewClickListener(onItemViewClickListener: (MedicineCost) -> Unit) {
        this.onItemClickListener = onItemViewClickListener
    }

    fun setPharma(data: List<MedicineCost>) {
        pharmaData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PharmaViewHolder {
        val itemBinding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PharmaViewHolder(itemBinding)
    }

    inner class PharmaViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cost: MedicineCost) {
            binding.apply {
                nameOnImg.text = cost.medicament.tradeName
                priceOnImg.text = cost.price
                root.setOnClickListener {
                    onItemClickListener(cost)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: PharmaViewHolder, position: Int) {
        holder.bind(pharmaData[position])
    }

    override fun getItemCount(): Int {
        return pharmaData.size
    }
}