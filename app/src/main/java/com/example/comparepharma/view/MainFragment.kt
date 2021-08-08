package com.example.comparepharma.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.comparepharma.R
import com.example.comparepharma.databinding.MainFragmentBinding
import com.example.comparepharma.model.AppState
import com.example.comparepharma.model.data.MedicineCost
import com.example.comparepharma.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val  viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    private var _binding: MainFragmentBinding? = null
    private val binding
        get() = _binding!!
    private val adapter = PharmaAdapter()
    private var isDataSetAptekaRu: Boolean = true

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
        adapter.removeOnItemViewClickListener()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter.setOnItemViewClickListener(object : OnItemViewClickListener {
            override fun onItemViewClick(cost: MedicineCost) {
                val manager = activity?.supportFragmentManager
                if (manager != null) {
                    val bundle = Bundle()
                    bundle.putParcelable(DetailsFragment.BUNDLE_EXTRA, cost)
                    manager.beginTransaction()
                        .add(R.id.container, DetailsFragment.newInstance(bundle))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
            }
        })

        binding.recyclerView.adapter = adapter
        binding.mainFragmentFAB.setOnClickListener {
            changeAptekaDataSet()
        }

        super.onViewCreated(view, savedInstanceState)

        val observer = Observer<AppState> { renderData(it) }
        viewModel.getDate().observe(viewLifecycleOwner, observer)
        viewModel.getPharmaFromLocalAptekaApril()
    }

    private fun changeAptekaDataSet() {
        if (isDataSetAptekaRu) {
            viewModel.getPharmaFromLocalAptekaRu()
            binding.mainFragmentFAB.setImageResource(R.drawable.apteka_ru)
        } else {
            viewModel.getPharmaFromLocalAptekaApril()
            binding.mainFragmentFAB.setImageResource(R.drawable.apteka_april)
        }
        isDataSetAptekaRu = !isDataSetAptekaRu
    }

    private fun renderData(data: AppState) {
        when (data) {
            is AppState.Success -> {
                val pharmaData = data.pharmaData
                binding.loadingLayout.visibility = View.GONE
                adapter.setPharma(pharmaData)
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(
                    binding.mainFragmentFAB,
                    getString(R.string.error),
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction("Reload") {
                        if (isDataSetAptekaRu) {
                            viewModel.getPharmaFromLocalAptekaRu()
                        } else {
                            viewModel.getPharmaFromLocalAptekaApril()
                        }
                    }
                    .show()
            }
        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(cost: MedicineCost)
    }
}