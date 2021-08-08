package com.example.comparepharma.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.comparepharma.databinding.ItemBinding
import com.example.comparepharma.model.data.MedicineCost

class PharmaAdapter :
    RecyclerView.Adapter<PharmaAdapter.PharmaViewHolder>() {

    private var pharmaData: List<MedicineCost> = listOf()
    private var onItemClickListener: MainFragment.OnItemViewClickListener? = null

    fun setOnItemViewClickListener(onItemViewClickListener: MainFragment.OnItemViewClickListener) {
        this.onItemClickListener = onItemViewClickListener
    }

    fun removeOnItemViewClickListener() {
        onItemClickListener = null
    }

    fun setPharma(data: List<MedicineCost>) {
        pharmaData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PharmaViewHolder {
        val itemBinding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PharmaViewHolder(itemBinding)
    }

    inner class PharmaViewHolder(private val item: ItemBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun bind(cost: MedicineCost) {
            item.nameOnImg.text = cost.medicament.tradeName
            item.priceOnImg.text = cost.price.toString()
            item.root.setOnClickListener {
                onItemClickListener?.onItemViewClick(cost)
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