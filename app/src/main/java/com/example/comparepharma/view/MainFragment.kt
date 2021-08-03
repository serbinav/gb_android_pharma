package com.example.comparepharma.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.comparepharma.R
import com.example.comparepharma.adapter.PharmaAdapter
import com.example.comparepharma.databinding.MainFragmentBinding
import com.example.comparepharma.model.AppState
import com.example.comparepharma.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var recyclerPharma: RecyclerView
    private lateinit var adapter: PharmaAdapter

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

        recyclerPharma = binding.recyclerView

        val observer = Observer<AppState> { renderData(it) }
        viewModel.getDate().observe(viewLifecycleOwner, observer)
        viewModel.getPharmaFromRemote()
    }

    private fun renderData(data: AppState) {
        when (data) {
            is AppState.Success -> {
                val pharmaData = data.pharmaData
                binding.loadingLayout.visibility = View.GONE

                adapter = PharmaAdapter.getInstance(pharmaData)
                recyclerPharma.adapter = adapter
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.main, getString(R.string.error), Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") {
                        viewModel.getPharmaFromRemote()
                    }
                    .show()
            }
        }
    }
}