package com.example.comparepharma.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.comparepharma.databinding.ItemBinding
import com.example.comparepharma.model.data.Cost

class PharmaAdapter :
    RecyclerView.Adapter<PharmaAdapter.PharmaViewHolder>() {

    private var pharmaData: List<Cost> = listOf()
    private var onItemClickListener: MainFragment.onItemViewClickListener? = null

    fun setOnItemViewClickListener(onItemViewClickListener: MainFragment.onItemViewClickListener) {
        this.onItemClickListener = onItemViewClickListener
    }

    fun removeOnItemViewClickListener() {
        onItemClickListener = null
    }

    fun setPharma(data: List<Cost>) {
        pharmaData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PharmaViewHolder {
        val itemBinding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PharmaViewHolder(itemBinding)
    }

    inner class PharmaViewHolder(private val item: ItemBinding) : RecyclerView.ViewHolder(item.root) {
        fun bind(cost: Cost) {
            item.nameOnImg.text = cost.medicament.name
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