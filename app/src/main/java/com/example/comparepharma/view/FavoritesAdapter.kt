package com.example.comparepharma.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.comparepharma.databinding.FavoritesItemBinding
import com.example.comparepharma.model.data.MedicineCost

class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    private var data: List<MedicineCost> = arrayListOf()

    fun setData(data: List<MedicineCost>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritesAdapter.FavoritesViewHolder {
        val binding =
            FavoritesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return FavoritesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesAdapter.FavoritesViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class FavoritesViewHolder(private val binding: FavoritesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: MedicineCost) {
            with(binding) {
                medicineId.text = data.medicament.id
                tradeName.text = data.medicament.tradeName
                price.text = data.price
            }
        }
    }
}