package com.example.comparepharma.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.comparepharma.databinding.PhoneBookItemBinding

class ContactAdapter : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    var contacts: List<String> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ContactViewHolder(private val binding: PhoneBookItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String) {
            binding.contactName.text = name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding =
            PhoneBookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount(): Int = contacts.size
}