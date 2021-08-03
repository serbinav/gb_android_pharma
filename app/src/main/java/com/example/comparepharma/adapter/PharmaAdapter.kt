package com.example.comparepharma.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.comparepharma.databinding.ItemBinding
import com.example.comparepharma.model.data.Cost

class PharmaAdapter(private val repository: List<Cost>) :
    RecyclerView.Adapter<PharmaAdapter.PharmaViewHolder>() {

    class PharmaViewHolder(private val item: ItemBinding) : RecyclerView.ViewHolder(item.root) {
        private val img: ImageView
        private val name: TextView
        private val price: TextView

        init {
            img = item.imageView
            name = item.nameOnImg
            price = item.priceOnImg
        }

        fun bind(cost: Cost) {
            name.text = cost.medicament.name
            price.text = cost.price.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PharmaViewHolder {
        val itemBinding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PharmaViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: PharmaViewHolder, position: Int) {
        holder.bind(repository[position])
    }

    override fun getItemCount(): Int {
        return repository.size
    }

    companion object Factory {
        fun getInstance(repository: List<Cost>): PharmaAdapter {
            return PharmaAdapter(repository)
        }
    }
}