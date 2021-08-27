package com.example.comparepharma.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.comparepharma.R
import com.example.comparepharma.databinding.MainFragmentItemBinding
import com.example.comparepharma.model.data.MedicineCost
import com.squareup.picasso.Picasso

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
        val itemBinding = MainFragmentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PharmaViewHolder(itemBinding)
    }

    inner class PharmaViewHolder(private val binding: MainFragmentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cost: MedicineCost) {
            binding.apply {
                if (cost.medicament.photo == "") {
                    Picasso
                        .get()
                        .load(R.drawable.no_img_200_200)
                        .into(imageView)
                } else {
                    Picasso
                        .get()
                        .load(cost.medicament.photo + "/l")
                        .into(imageView)
                }
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