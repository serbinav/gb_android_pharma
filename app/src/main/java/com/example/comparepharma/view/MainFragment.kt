package com.example.comparepharma.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.comparepharma.repository.RepositorySingle
import com.example.comparepharma.adapter.PharmaAdapter
import com.example.comparepharma.databinding.MainFragmentBinding
import com.example.comparepharma.viewmodel.MainViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding : MainFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerPharma = view.findViewById<RecyclerView>(binding.recyclerView.id)
        val adapter = PharmaAdapter.getInstance(RepositorySingle)
        recyclerPharma.adapter = adapter

        val observer = Observer<String> { renderData(it)}
        viewModel.getDate().observe(viewLifecycleOwner, observer)

        binding.btnTest.setOnClickListener {
            viewModel.requestData("ПОЗИЦИЯ НАША! УРА!") }
    }

    private fun renderData(data : String) {
        binding.btnTest.text = data
    }
}