package com.example.comparepharma

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.comparepharma.data.Pharma
import com.example.comparepharma.repository.IRepository

class PharmaAdapter(private val repository: IRepository) :
    RecyclerView.Adapter<PharmaAdapter.PharmaViewHolder>() {
    class PharmaViewHolder(val item: View) : RecyclerView.ViewHolder(item) {
        private val img: ImageView
        private val name: TextView
        private val price: TextView

        init {
            img = item.findViewById(R.id.image_view)
            name = item.findViewById(R.id.name_on_img)
            price = item.findViewById(R.id.price_on_img)
        }

        fun bind(pharma: Pharma) {
            name.text = pharma.name
            price.text = pharma.price.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PharmaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return PharmaViewHolder(view)
    }

    override fun onBindViewHolder(holder: PharmaViewHolder, position: Int) {
        holder.bind(repository.getPharmas()[position])
    }

    override fun getItemCount(): Int {
        return repository.getPharmas().size
    }

    companion object Factory {
        private val NormalTemp: Float = 36.6F

        fun getInstance(repository: IRepository): PharmaAdapter {
            return PharmaAdapter(repository)
        }
    }
}