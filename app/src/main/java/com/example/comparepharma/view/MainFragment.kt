package com.example.comparepharma.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.comparepharma.R
import com.example.comparepharma.repository.RepositorySingle
import com.example.comparepharma.adapter.PharmaAdapter
import com.example.comparepharma.databinding.MainFragmentBinding
import com.example.comparepharma.viewmodel.MainViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding = MainFragmentBinding.inflate(onGetLayoutInflater(savedInstanceState))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerPharma = view.findViewById<RecyclerView>(binding.recyclerView.id)
        val adapter = PharmaAdapter.getInstance(RepositorySingle)
        recyclerPharma.adapter = adapter

        val observer = Observer<Any> { renderData(it)}
        viewModel.getDate().observe(viewLifecycleOwner, observer)
    }

    private fun renderData(data : Any) {
        Toast.makeText(context, "data", Toast.LENGTH_SHORT).show()
    }
}